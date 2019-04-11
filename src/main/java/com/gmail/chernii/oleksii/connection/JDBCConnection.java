package com.gmail.chernii.oleksii.connection;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Space on 10.04.2019.
 */
public class JDBCConnection {
    private static final Logger LOG = Logger.getLogger(JDBCConnection.class);

    public static Connection getConnection() {
        Connection connection = null;
        String url = property().getProperty("URL");
        String user = property().getProperty("USER");
        String password = property().getProperty("PASSWORD");
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return connection;
    }

    private static Properties property() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/property/config.properties")){
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return properties;
    }
}
