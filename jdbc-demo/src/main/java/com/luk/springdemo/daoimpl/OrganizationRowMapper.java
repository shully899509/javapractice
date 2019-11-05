package com.luk.springdemo.daoimpl;

import com.luk.springdemo.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationRowMapper implements RowMapper<Organization>{
    public Organization mapRow(ResultSet resultSet, int rownum) throws SQLException {
        Organization org = new Organization();
        org.setId(resultSet.getInt("id"));
        org.setName(resultSet.getString("name"));

        return org;
    }
}
