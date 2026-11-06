package com.rifki.java_project_task1.database;

import com.rifki.java_project_task1.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = DatabaseConfig.getActiveUrl();
            String user = DatabaseConfig.getActiveUsername();
            String pass = DatabaseConfig.getActivePassword();
            String driver = DatabaseConfig.getActiveDriver();

            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                System.err.println("Database driver not found: " + driver);
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            } finally {
                connection = null;
            }
        }
    }
}
