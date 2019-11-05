package com.luk.springdemo.daoimpl;

import com.luk.springdemo.Organization;
import com.luk.springdemo.dao.OrganizationDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

public class JdbcTemplateExceptionsDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-cp.xml");
        OrganizationDao dao = (OrganizationDaoImpl) ctx.getBean("orgDao");
        List<Organization> orgs = null;
                try {
                    orgs = dao.getAllOrganizations();
                } catch (BadSqlGrammarException e){
                    System.err.println("SUB EXCEPTIONS: " + e.getMessage());
                    System.err.println("SUB EXCEPTIONS: " + e.getClass());
                }
                catch (DataAccessException e){
                    System.err.println("EXCEPTION MESSAGE:"  + e.getMessage());
                    System.err.println("EXCEPTION CLASS: " + e.getClass());
                }
        for (Organization org : orgs){
            System.out.println(org);
        }

        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
