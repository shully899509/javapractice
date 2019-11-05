package com.luk.springdemo;

import com.luk.springdemo.dao.OrganizationDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DaoUtils {
    public final String createOperation = "CREATE";
    public final String readOperation = "READ";
    public final String updateOperation = "UPDATE";
    public final String deleteOperation = "DELETE";
    public final String cleanupOperation = "TRUNCATE";

    public void printOrganizations(List<Organization> orgs, String operation){
        System.out.println("\n print after " + operation + " operation \n");
        for (Organization org: orgs){
            System.out.println(org);
        }
    }

    public void printSuccessFailure(String operation, boolean param){
        if (param){
            System.out.println("op " + operation + " da");
        } else {
            System.out.println("op " + operation + " nu");
        }
    }

    public void createSeedData(OrganizationDao dao){
        Organization org1 = new Organization("Luca1");
        Organization org2 = new Organization("Luca2");
        Organization org3 = new Organization("Luca3");

        List<Organization> orgs = new ArrayList<Organization>();
        orgs.add(0, org1); orgs.add(1, org2); orgs.add(2, org3);

        int createCount = 0;
        for (Organization org : orgs){
            boolean isCreated = dao.create(org);
            if (isCreated){
                createCount += 1;
            }
        }

        System.out.println("Created " + createCount + " rows");
    }

    public void printOrganizationCount(List<Organization> orgs, String operation){
        System.out.println("\n ceva ceva plm pnmmm we have " + orgs.size() + " rows after " + operation + " operation pana mea");
    }
}
