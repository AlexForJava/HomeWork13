package com.gmail.chernii.oleksii;


import com.gmail.chernii.oleksii.data.DevelopersData;
import com.gmail.chernii.oleksii.data.ProjectsData;

/**
 * Created by Space on 10.04.2019.
 */
public class Application {
    public static void main(String[] args) {
        DevelopersData developersData = new DevelopersData();
        String projectName = "atb";
        String branch = "Java";
        String level = "middle";

        System.out.println(projectName + " developers salary = " + developersData.getDevelopersSallaryByProject(projectName));
        developersData.getDevelopersByProject(projectName).forEach(System.out::println);
        developersData.getDevelopersByBranch(branch).forEach(System.out::println);
        developersData.getDevelopersByLevel(level).forEach(System.out::println);
        new ProjectsData().getFormatedList().forEach(System.out::println);

    }
}
