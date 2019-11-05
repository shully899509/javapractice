package services;

import datarepository.Datasource;
import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private Connection connection;

    private static final String PERSONS_TABLE = "PERSONS";

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final int INDEX_ID = 1;
    private static final int INDEX_NAME = 2;

    private Datasource datasource;

    public PersonService() {
        this.datasource = new Datasource();
        this.connection = datasource.open();
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeDatasource() {
        datasource.close();
    }

    public List<Person> getPersons() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE);
        return getPeople(preparedStatement);
    }

    public List<Person> getPersonByName(String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE + " WHERE " + NAME + " = ?");
        preparedStatement.setString(1, name);
        return getPeople(preparedStatement);
    }

    public Person getPersonById(int Id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE + " WHERE " + ID + " = ?")) {
            preparedStatement.setInt(1, Id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
            } else {
                throw new RuntimeException("No Person with ID " + Id + " found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }


    private List<Person> getPeople(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(INDEX_ID));
                person.setName(rs.getString(INDEX_NAME));
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }

    public Person insertPerson(Person person) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + PERSONS_TABLE + '(' + NAME + ") VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
            } else {
                throw new RuntimeException("Error inserting Person: " + person.getId() + " " + person.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    public void deletePerson(Person person) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + PERSONS_TABLE + " WHERE " + ID + " = ?")) {
            preparedStatement.setInt(1, person.getId());
            if (preparedStatement.executeUpdate() == 0){
                throw new RuntimeException("Person " + person.getId() + " " + person.getName() + " not found");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting row from Persons table: " + e.getMessage());
        }
    }

    public Person updatePerson(Person person, String nameReplace) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + PERSONS_TABLE + " SET " + NAME + " = ? WHERE " + ID + " = ? ", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nameReplace);
            preparedStatement.setInt(2, person.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
            } else {
                throw new RuntimeException("Error updating Person: " + person.getId() + " " + person.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
