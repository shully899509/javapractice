package com.luk.springdemo.dao;

import com.luk.springdemo.Organization;

import javax.sql.DataSource;
import java.util.List;

public interface OrganizationDao {
    //set data source
    public void setDataSource(DataSource ds);

    //create record
    boolean create(Organization org);

    //retrieve single organization
    Organization getOrganization(Integer id);

    List<Organization> getAllOrganizations();

    boolean delete(Organization org);

    boolean update(Organization org);

    void cleanup();
}
