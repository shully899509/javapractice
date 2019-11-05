package com.javapractice.practice2.services;

import com.javapractice.practice2.model.Person;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public interface PersonService {
    //void setDataSource(BasicDataSource dataSource);
    boolean create(Person person);
    Person getPerson(Integer id);
    List<Person> getAllPersons();
    boolean delete(Person person);
    boolean update(Person person);
    void cleanup();
}
