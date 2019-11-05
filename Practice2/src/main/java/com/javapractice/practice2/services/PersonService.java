package com.javapractice.practice2.services;

import com.javapractice.practice2.model.Person;

import javax.sql.DataSource;
import java.util.List;

public interface PersonService {
    void setDataSource(DataSource ds);
    boolean create(Person person);
    Person getPerson(Integer id);
    List<Person> getAllPersons();
    boolean delete(Person person);
    boolean update(Person person);
    void cleanup();
}
