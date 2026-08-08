package com.virag.banking.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Single place responsible for knowing how to open a JDBC connection.
 * Replaces the old DBcon class. Nothing else in the app should touch
 * DriverManager directly.
 */
public final class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/coep";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD"; // TODO: move to a config file / env var, don't hardcode in real projects

    private DBConnection() {
        // utility class, no instances
    }

    /**
     * Opens a new connection. Callers are responsible for closing it
     * (use try-with-resources) — this class does not pool connections.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
