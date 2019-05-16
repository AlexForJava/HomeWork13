package com.gmail.chernii.oleksii.data;

import com.gmail.chernii.oleksii.connection.JDBCConnection;
import com.gmail.chernii.oleksii.entity.Skill;
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
public class SkillsData {
    private static final Logger LOG = Logger.getLogger(SkillsData.class);

    private static final String ADD_SKILL_SQL = "INSERT INTO skills VALUES (?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM skills";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM skills WHERE id=?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE skills SET branch=?, level=? WHERE id=?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM projects WHERE id=?";

    public void addSkillToDatabase(Skill skill) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SKILL_SQL)) {
            preparedStatement.setString(1, skill.getBranch());
            preparedStatement.setString(2, skill.getLevel());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(SkillsData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                skills.add(skillFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return skills;
    }

    public Skill getSkillByID(int id) {
        Skill skill = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setInt(1, id);
            resultSet.next();
            skill = skillFromResultSet(resultSet);
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return skill;
    }

    public void updateSkillByID(int id, Skill skill) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            preparedStatement.setString(1, skill.getBranch());
            preparedStatement.setString(2, skill.getLevel());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public void deleteSkillById(int id) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    private Skill skillFromResultSet(ResultSet resultSet) {
        Skill skill = new Skill();
        try {
            skill.setBranch(resultSet.getString("branch"));
            skill.setLevel(resultSet.getString("level"));
        } catch (SQLException e) {
            LOG.error(SkillsData.class.getSimpleName() + " " + e.getMessage());
        }
        return skill;
    }
}
