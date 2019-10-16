package main.java.tests;

import main.java.model.Person;
import main.java.services.PersonService;
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
        try {
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            Person actualPerson = personService.getPersonById(expectedPerson.getId());
            Assertions.assertEquals(expectedPerson, actualPerson);
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }

    @Test
    public void testUpdatePerson() throws SQLException {
        PersonService personService = new PersonService();
        Connection connection = personService.getConnection();
        connection.setAutoCommit(false);
        Person expectedPerson = new Person("Luca");
        String updatedName = "Alex";
        try {
            Person insertPerson = personService.insertPerson(expectedPerson);
            Person actualPerson = personService.updatePerson(insertPerson, updatedName);
            Assertions.assertEquals(updatedName, actualPerson.getName());
            Assertions.assertEquals(insertPerson.getId(), actualPerson.getId());
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

        try {
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            personService.deletePerson(expectedPerson);
            Exception exception = Assertions.assertThrows(RuntimeException.class, () -> personService.deletePerson(expectedPerson));
            Assertions.assertEquals("Person " + expectedPerson.getId() + " " + expectedPerson.getName() + " not found", exception.getMessage());
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }

    @Test
    public void findNoPerson() throws SQLException {
        PersonService personService = new PersonService();
        Connection connection = personService.getConnection();
        connection.setAutoCommit(false);
        Person expectedPerson = new Person("Luca");

        try {
            Person insertPerson = personService.insertPerson(expectedPerson);
            expectedPerson.setId(insertPerson.getId());
            personService.deletePerson(expectedPerson);

            Exception exception = Assertions.assertThrows(RuntimeException.class, () -> personService.getPersonById(expectedPerson.getId()));
            Assertions.assertEquals("No Person with ID " + expectedPerson.getId() + " found.", exception.getMessage());
        } finally {
            connection.rollback();
            personService.closeDatasource();
        }
    }
}