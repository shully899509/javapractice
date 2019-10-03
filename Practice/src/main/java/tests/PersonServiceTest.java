package main.java.tests;

import main.java.model.PersonService;
import main.java.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class PersonServiceTest {

    @Test
    public void testInsertPerson() throws SQLException {
        PersonService personService = new PersonService();
        Connection connection = personService.getConnection();
        connection.setAutoCommit(false);
        Person expectedPerson = new Person("Luca");
        Person actualPerson = new Person();
        try {
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            actualPerson = personService.queryPersonsById(expectedPerson.getId());
            Assertions.assertEquals(expectedPerson, actualPerson);
        } catch (SQLException e) {
            System.out.println("Error insert row test: " + e.getMessage());
        } catch (AssertionError e) {
            System.err.println(e);
            System.err.println("Expected Person: " + expectedPerson.getId() + " " + expectedPerson.getName());
            System.err.println("Actual Person: " + actualPerson.getId() + " " + actualPerson.getName());
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }

    @Test
    public void testTwoConnectionsWithInsert() throws SQLException {
        PersonService personService1 = new PersonService();
        PersonService personService2 = new PersonService();
        Connection connection1 = personService1.getConnection();
        Connection connection2 = personService2.getConnection();
        connection1.setAutoCommit(false);
        connection2.setAutoCommit(false);

        Person expectedPerson1 = new Person("Luca");
        Person expectedPerson2 = new Person("Alex");
        Person actualPerson1 = new Person();
        Person actualPerson2 = new Person();
        Person personInDifferentTransaction = new Person();
        try{
            Person insertPerson1 = personService1.insertPerson(expectedPerson1);
            expectedPerson1.setId(insertPerson1.getId());
            Person insertPerson2 = personService2.insertPerson(expectedPerson2);
            expectedPerson2.setId(insertPerson2.getId());

            actualPerson1 = personService1.queryPersonsById(expectedPerson1.getId());
            Assertions.assertEquals(expectedPerson1, actualPerson1);

            actualPerson2 = personService2.queryPersonsById(expectedPerson2.getId());
            Assertions.assertEquals(actualPerson2, expectedPerson2);

            personInDifferentTransaction = personService2.queryPersonsById(expectedPerson1.getId());
            Assertions.assertEquals(personInDifferentTransaction, new Person());
        } catch(SQLException e){
            System.out.println("Error testTwoConnectionsWithInsert: " + e.getMessage());
        } catch (AssertionError e){
            System.err.println(e);
            System.err.println("Expected Person 1: " + expectedPerson1.getId() + " " + expectedPerson1.getName());
            System.err.println("Actual Person 1: " + actualPerson1.getId() + " " + actualPerson1.getName());
            System.err.println("Expected Person 2: " + expectedPerson2.getId() + " " + expectedPerson2.getName());
            System.err.println("Actual Person 2: " + actualPerson2.getId() + " " + actualPerson2.getName());
            System.err.println("Person 1 in Transaction 2: " + personInDifferentTransaction.getId() + " " + personInDifferentTransaction.getName());
        } finally {
            connection1.rollback();
            connection2.rollback();
            personService1.closeDatasource();
            personService2.closeDatasource();
        }

    }

    @Test
    public void testUpdatePerson() throws SQLException {
        PersonService personService = new PersonService();
        Connection connection = personService.getConnection();
        connection.setAutoCommit(false);
        Person expectedPerson = new Person("Luca");
        Person actualPerson = new Person();
        try{
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            actualPerson = personService.queryPersonsById(expectedPerson.getId());
            Assertions.assertEquals(expectedPerson, actualPerson);

            actualPerson = personService.updatePerson(expectedPerson, "Alex");
            expectedPerson.setName("Alex");
            Assertions.assertEquals(expectedPerson, actualPerson);
        } catch (SQLException e){
            System.err.println("Error in Update Person test" + e);
        } catch (AssertionError e) {
            System.err.println(e);
            System.err.println("Expected Person: " + expectedPerson.getId() + " " + expectedPerson.getName());
            System.err.println("Actual Person: " + actualPerson.getId() + " " + actualPerson.getName());
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }

    @Test
    public void testDeletePerson() throws SQLException {
        PersonService personService = new PersonService();
        Connection connection = personService.getConnection();
        connection.setAutoCommit(false);
        Person expectedPerson = new Person("Luca");
        Person actualPerson = new Person();
        try{
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            actualPerson = personService.queryPersonsById(expectedPerson.getId());
            Assertions.assertEquals(expectedPerson, actualPerson);

            personService.deletePerson(expectedPerson);
            actualPerson = personService.queryPersonsById(expectedPerson.getId());
            Assertions.assertEquals(actualPerson, new Person());
        } catch (SQLException e){
            System.err.println("Error in test Delete Person: " + e.getMessage());
        } catch (AssertionError e) {
            System.err.println(e);
            System.err.println("Expected Person: " + expectedPerson.getId() + " " + expectedPerson.getName());
            System.err.println("Actual Person: " + actualPerson.getId() + " " + actualPerson.getName());
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }
}