package ru.clevertec.check;

import java.sql.*;
import java.util.Properties;

import org.postgresql.Driver;

public class SqlQueries {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static Connection connection;

    protected static Connection getConnection(String datasourceUrl, String datasourceUsername, String datasourcePassword) {
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName(DB_DRIVER);
            Properties props = new Properties();
            props.setProperty("user", datasourceUsername);
            props.setProperty("password", datasourcePassword);
            return DriverManager.getConnection(datasourceUrl, props);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
