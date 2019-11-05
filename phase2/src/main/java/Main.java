package main.java;

import main.java.services.PersonService;
import main.java.model.Person;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        PersonService crud = new PersonService();

        List<Person> persons = crud.getPersons();
        printPersons(persons, "intial read: ");


        Person luca = new Person();
        luca.setName("Luca");

        System.out.println("inserting: ");
        Person result = crud.insertPerson(luca);
        System.out.println(result.getId() + " " + result.getName());
        System.out.println(" ");


        persons = crud.getPersons();
        printPersons(persons, "after insert: ");


        String alex = "Alex";
        System.out.println("updating " + luca.getName() + " to " + alex);
        persons = crud.getPersonByName("Luca");
        printPersons(persons, "before update: ");
        crud.updatePerson(luca, alex);
        persons = crud.getPersons();
        printPersons(persons, "after update:");

        luca.setName(alex);
        System.out.println("searching for " + luca.getName());
        persons = crud.getPersonByName(luca.getName());
        printPersons(persons, "list of persons searched by name " + luca.getName());


        crud.deletePerson(luca);
        persons = crud.getPersons();
        printPersons(persons, "after deleting " + luca.getName());

        crud.closeDatasource();
    }

    public static void printPersons(List<Person> persons, String msg) {
        System.out.println(msg);
        for (Person person : persons) {
            System.out.println(person.getId() + " " + person.getName());
        }
        System.out.println(" ");
    }

}
