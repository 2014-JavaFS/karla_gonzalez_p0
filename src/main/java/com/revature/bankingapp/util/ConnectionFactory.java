package com.revature.bankingapp.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final Properties properties = new Properties();

    /**
     * Private constructor used to initialize the database properties.
     * Reads database configuration from the 'db.properties' file.
     * If the file is not found or cannot be read, an IOException will be caught and logged.
     */
    private ConnectionFactory() {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static block used for loading the PostgreSQL JDBC driver every time the class is loaded.
     * A ClassNotFoundException will be caught and logged if the class is not found.
     */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Singleton instance of ConnectionFactory
     *
     * @return An instance of ConnectionFactory
     */
    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Connects to the database using the properties loaded during initialization.
     * Will catch and log an SQLException if a connection cannot be established.
     *
     * @return The database connection or null if an exception occurs
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
