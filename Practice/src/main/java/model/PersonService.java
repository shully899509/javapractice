package main.java.model;

import main.java.datarepository.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private Connection connection;
    private PreparedStatement preparedStatement;

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

    public List<Person> queryPersons() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE);
        return getPeople();
    }

    public List<Person> queryPersonsByName(String name) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE + " WHERE " + NAME + " = ?");
        preparedStatement.setString(1, name);
        return getPeople();
    }

    public Person queryPersonsById(int Id) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + PERSONS_TABLE + " WHERE " + ID + " = ?");
        preparedStatement.setInt(1, Id);
        try {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
        } catch (SQLException e) {
            System.err.println("Person with id " + Id + " not found.");
            return new Person();
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }

    }

    private List<Person> getPeople() throws SQLException {
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

    public Person insertPerson(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO " + PERSONS_TABLE + '(' + NAME + ") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, person.getName());

        try {
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
        } catch (SQLException e) {
            System.err.println("Error inserting row in Persons table: " + e.getMessage());
            return new Person();
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }

    public void deletePerson(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement("DELETE FROM " + PERSONS_TABLE + " WHERE " + NAME + " = ?");
        preparedStatement.setString(1, person.getName());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting row from Persons table: " + e.getMessage());
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }

    public Person updatePerson(Person person, String nameReplace) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE " + PERSONS_TABLE + " SET " + NAME + " = ? WHERE " + NAME + " = ? ", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, nameReplace);
        preparedStatement.setString(2, person.getName());

        try {
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return new Person(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME));
        } catch (SQLException e) {
            System.err.println("Error updating Persons table: " + e.getMessage());
            return new Person();
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }
}
