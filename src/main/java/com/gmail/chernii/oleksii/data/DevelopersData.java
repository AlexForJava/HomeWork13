package com.gmail.chernii.oleksii.data;

import com.gmail.chernii.oleksii.connection.JDBCConnection;
import com.gmail.chernii.oleksii.entity.Developer;
import com.gmail.chernii.oleksii.entity.Sex;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Space on 11.04.2019.
 */
public class DevelopersData {
    private static final Logger LOG = Logger.getLogger(DevelopersData.class);

    private static final String ADD_DEVELOPER_SQL = "INSERT INTO developers VALUES (?,?,?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM developers";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM developers WHERE name=?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE developers SET name=?, age=?, male=?, salary=? WHERE id=?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM developers WHERE id=?";

    private static final String SALARY_BY_PROJECT_SQL = "SELECT sum(salary), project_name FROM dev_proj" +
            " INNER JOIN developers ON developers.id = dev_proj.dev_id" +
            " INNER JOIN projects ON projects.id = dev_proj.proj_id" +
            " WHERE project_name=?";

    private static final String DEVELOPERS_BY_PROJECT = "SELECT name FROM dev_proj " +
            " INNER JOIN developers ON developers.id = dev_proj.dev_id " +
            " INNER JOIN projects ON projects.id = dev_proj.proj_id " +
            "WHERE project_name=?";

    private static final String DEVELOPERS_BY_BRANCH = "SELECT name FROM dev_skills " +
            " LEFT JOIN developers ON developers.id = dev_skills.dev_id " +
            " LEFT JOIN skills ON skills.id = dev_skills.skill_id " +
            " WHERE branch=?";

    private static final String DEVELOPERS_BY_LEVEL = "SELECT name FROM dev_skills" +
            " LEFT JOIN developers ON developers.id = dev_skills.dev_id" +
            " LEFT JOIN skills ON skills.id = dev_skills.skill_id" +
            " WHERE level=?";


    public void addDeveloperToDatabase(Developer developer) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DEVELOPER_SQL)) {
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setInt(3, developer.getSex() == Sex.MALE ? 1 : 0);
            preparedStatement.setInt(4, developer.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<Developer> getAllDevelopers() {
        List<Developer> developers = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                developers.add(developerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
        return developers;
    }

    public Developer getDevelopersByName(String name) {
        Developer developer = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setString(1, name);
            while (resultSet.next()) {
                developer = developerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
        return developer;
    }

    public void updateDeveloperByID(int id, Developer developer) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setInt(3, developer.getSex() == Sex.MALE ? 1 : 0);
            preparedStatement.setInt(4, developer.getSalary());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public void deleteDeveloperByID(int id) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public int getDevelopersSallaryByProject(String projectName) {
        int result = 0;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SALARY_BY_PROJECT_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setString(1, projectName);
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
        return result;
    }

    public List<Developer> getDevelopersByProject(String projectName) {
        return developersSQL(DEVELOPERS_BY_PROJECT, projectName);
    }

    public List<Developer> getDevelopersByBranch(String branch) {
        return developersSQL(DEVELOPERS_BY_BRANCH, branch);
    }

    public List<Developer> getDevelopersByLevel(String level) {
        return developersSQL(DEVELOPERS_BY_LEVEL, level);
    }

    private List<Developer> developersSQL(String sql, String value) {
        List<Developer> developers = new ArrayList<>();
        List<String> developersName = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setString(1, value);
            while (resultSet.next()) {
                developersName.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }
        for (String name : developersName) {
            developers.add(getDevelopersByName(name));
        }
        return developers;
    }

    private Developer developerFromResultSet(ResultSet resultSet) {
        Developer developer = new Developer();
        try {
            developer.setName(resultSet.getString("name"));
            developer.setAge(resultSet.getInt("age"));
            developer.setSex(resultSet.getInt("male") == 1 ? Sex.MALE : Sex.FEMALE);
            developer.setSalary(resultSet.getInt("salary"));
        } catch (SQLException e) {
            LOG.error(Developer.class.getSimpleName() + " " + e.getMessage());
        }

        return developer;
    }
}
