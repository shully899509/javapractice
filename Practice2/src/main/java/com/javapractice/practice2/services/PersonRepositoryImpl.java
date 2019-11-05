package com.javapractice.practice2.services;

import com.javapractice.practice2.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Service
public class PersonServiceImpl implements PersonService {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Person person) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(person);
        String sqlQuery = "INSERT INTO persons(NAME) VALUES (:name)";
        return namedParameterJdbcTemplate.update(sqlQuery, params) == 1;
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
