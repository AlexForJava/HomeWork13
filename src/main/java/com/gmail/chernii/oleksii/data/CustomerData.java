package com.gmail.chernii.oleksii.data;

import com.gmail.chernii.oleksii.connection.JDBCConnection;
import com.gmail.chernii.oleksii.entity.Company;
import com.gmail.chernii.oleksii.entity.Customer;
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
public class CustomerData {
    private static final Logger LOG = Logger.getLogger(CustomerData.class);

    private static final String ADD_CUSTOMER_SQL = "INSERT INTO customers VALUES (?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE id=?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET customer_name=?, country=? WHERE id=?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM customers WHERE id=?";

    public void addCustomerToDatabase(Customer customer) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getCountry());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CustomerData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                customers.add(customerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CustomerData.class.getSimpleName() + " " + e.getMessage());
        }
        return customers;
    }

    public Customer getCustomerByID(int id) {
        Customer customer = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setInt(1, id);
            resultSet.next();
            customer = customerFromResultSet(resultSet);
        } catch (SQLException e) {
            LOG.error(CustomerData.class.getSimpleName() + " " + e.getMessage());
        }
        return customer;
    }

    public void updateCustomerByID(int id, Customer customer) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getCountry());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CustomerData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    public void deleteCustomerById(int id) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(CustomerData.class.getSimpleName() + " " + e.getMessage());
        }
    }

    private Customer customerFromResultSet(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setName(resultSet.getString("customer_name"));
            customer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            LOG.error(CompaniesData.class.getSimpleName() + " " + e.getMessage());
        }
        return customer;
    }
}
