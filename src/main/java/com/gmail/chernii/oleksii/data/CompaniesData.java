package com.gmail.chernii.oleksii.data;

import com.gmail.chernii.oleksii.connection.JDBCConnection;
import com.gmail.chernii.oleksii.entity.Company;
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
public class CompaniesData {
    private static final Logger LOG = Logger.getLogger(CompaniesData.class);

    private static final String ADD_COMPANY_SQL = "INSERT INTO companies VALUES (?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM companies";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM companies WHERE id=?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE companies SET name=?, location=? WHERE id=?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM companies WHERE id=?";

    public void addCompanyToDatabase(Company company) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMPANY_SQL)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getLocation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<Company> getAllSkills() {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                companies.add(compayFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return companies;
    }

    public Company getCompanyByID(int id) {
        Company company = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setInt(1, id);
            resultSet.next();
            company = compayFromResultSet(resultSet);
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return company;
    }

    public void updateCompanyByID(int id, Company company) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getLocation());
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
            LOG.error(SkillsData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    private Company compayFromResultSet(ResultSet resultSet) {
        Company company = new Company();
        try {
            company.setName(resultSet.getString("company_name"));
            company.setLocation(resultSet.getString("location"));
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return company;
    }
}
