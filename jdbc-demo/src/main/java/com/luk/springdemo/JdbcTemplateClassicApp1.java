package com.luk.springdemo;

import com.luk.springdemo.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTemplateClassicApp1 {

    @Autowired
    private OrganizationDao dao;

    @Autowired
    private DaoUtils daoUtils;

    public void actionMethod() {
        daoUtils.createSeedData(dao);
        List<Organization> orgs = dao.getAllOrganizations();
        daoUtils.printOrganizations(orgs, daoUtils.readOperation);

        Organization org = new Organization("PanaMea Inc.");
        boolean isCreated = dao.create(org);
        daoUtils.printSuccessFailure(daoUtils.createOperation, isCreated);
        daoUtils.printOrganizationCount(dao.getAllOrganizations(), daoUtils.createOperation);

        Organization org2 = dao.getOrganization(22);
        System.out.println(org2);

        dao.cleanup();
        daoUtils.printOrganizationCount(dao.getAllOrganizations(), daoUtils.cleanupOperation);
    }

    public static void main(String[] args) {

        //app context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-cp.xml");

        //OrganizationDao dao = (OrganizationDao) ctx.getBean("orgDao");
        JdbcTemplateClassicApp1 mainApp = ctx.getBean(JdbcTemplateClassicApp1.class);
        mainApp.actionMethod();

        //close context
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
