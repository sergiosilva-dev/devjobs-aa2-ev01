package com.devjobs.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Properties P = new Properties();
    static {
        try {
            P.load(ConnectionFactory.class.getResourceAsStream("/db.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("DB config error", e);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(
                P.getProperty("DB_URL"),
                P.getProperty("DB_USER"),
                P.getProperty("DB_PASSWORD"));
    }
}