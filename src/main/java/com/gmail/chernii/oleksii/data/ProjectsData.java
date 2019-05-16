package com.gmail.chernii.oleksii.data;

import com.gmail.chernii.oleksii.connection.JDBCConnection;
import com.gmail.chernii.oleksii.entity.Project;
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
public class ProjectsData {
    private static final Logger LOG = Logger.getLogger(ProjectsData.class);

    private static final String ADD_PROJECT_SQL = "INSERT INTO projects VALUES (?,?,?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM projects";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM projects WHERE id=?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE projects SET project_name=?, type=?, cost=?, date=? WHERE id=?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM projects WHERE id=?";
    private static final String FORMATED_PROJECT_SQL = "SELECT date, project_name, count(developers.id) FROM dev_proj " +
            "  LEFT JOIN projects ON projects.id = dev_proj.proj_id" +
            "  LEFT JOIN developers ON developers.id = dev_proj.dev_id" +
            "GROUP BY project_name;";

    public void addProjectToData(Project project) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PROJECT_SQL)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getType());
            preparedStatement.setInt(3, project.getCost());
            preparedStatement.setDate(4, project.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                projects.add(projectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }
        return projects;
    }

    public Project getProjectByID(int id) {
        Project project = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setInt(1, id);
            resultSet.next();
            project = projectFromResultSet(resultSet);
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }
        return project;
    }

    public void updateProjectByID(int id, Project project) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getType());
            preparedStatement.setInt(3, project.getCost());
            preparedStatement.setDate(4, project.getDate());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public void deleteProjectByID(int id) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<String> getFormatedList() {
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FORMATED_PROJECT_SQL)) {
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getDate(1) + "\t")
                        .append(resultSet.getString(2) + "\t")
                        .append(resultSet.getInt(3));
                list.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } catch (SQLException e) {
            LOG.error(ProjectsData.class.getSimpleName() + " " + e.getMessage());
        }

        return list;
    }

    private Project projectFromResultSet(ResultSet resultSet) {
        Project project = new Project();
        try {
            project.setName(resultSet.getString("project_name"));
            project.setType(resultSet.getString("type"));
            project.setCost(resultSet.getInt("cost"));
            project.setDate(resultSet.getDate("date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }
}
