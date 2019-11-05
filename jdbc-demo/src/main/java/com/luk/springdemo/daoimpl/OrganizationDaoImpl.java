package com.luk.springdemo.daoimpl;

import com.luk.springdemo.Organization;
import com.luk.springdemo.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean create(Organization org) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(org);
        String sqlQuery = "INSERT INTO organizations(NAME) VALUES (:name)";
        //Object[] args = new Object[]{org.getName()};
        return namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1;
                //jdbcTemplate.update(sqlQuery, args) == 1; //returns number of affected records, we are inserting only 1 record
    }

    public Organization getOrganization(Integer id) {
        SqlParameterSource params = new MapSqlParameterSource("ID", id);
        String sqlQuery = "SELECT id, name FROM organizations WHERE id = :ID";
        //Object[] args = new Object[] {id};
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new OrganizationRowMapper());
                //jdbcTemplate.queryForObject(sqlQuery, args, new OrganizationRowMapper());
    }

    public List<Organization> getAllOrganizations() {
        String sqlQuery = "SELECT * FROM organizations";
        return namedParameterJdbcTemplate.query(sqlQuery, new OrganizationRowMapper());
                //jdbcTemplate.query(sqlQuery, new OrganizationRowMapper());
    }

    public boolean delete(Organization org) {
        String sqlQuery = "DELETE FROM organizations WHERE id = :ID";
        SqlParameterSource param = new BeanPropertySqlParameterSource(org);
        //Object[] args = new Object[] {org.getId()};
        return namedParameterJdbcTemplate.update(sqlQuery, param) == 1;
                //jdbcTemplate.update(sqlQuery, args) == 1;
    }

    public boolean update(Organization org) {
        String sqlQuery = "UPDATE organizations SET name = :name WHERE id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(org);
        //Object[] args = new Object[] {org.getName(), org.getId()};
        return namedParameterJdbcTemplate.update(sqlQuery, params) == 1;
                //jdbcTemplate.update(sqlQuery, args) == 1;
    }

    public void cleanup() {
        String sqlQuery = "TRUNCATE TABLE organizations";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
