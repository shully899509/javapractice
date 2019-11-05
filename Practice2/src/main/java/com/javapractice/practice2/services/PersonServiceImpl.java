package com.javapractice.practice2.services;

import com.javapractice.practice2.model.Person;

import javax.sql.DataSource;
import java.util.List;

@
public class PersonServiceImpl implements PersonService {
    @Override
    public void setDataSource(DataSource ds) {

    }

    @Override
    public boolean create(Person person) {
        return false;
    }

    @Override
    public Person getPerson(Integer id) {
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return null;
    }

    @Override
    public boolean delete(Person person) {
        return false;
    }

    @Override
    public boolean update(Person person) {
        return false;
    }

    @Override
    public void cleanup() {

    }
}
