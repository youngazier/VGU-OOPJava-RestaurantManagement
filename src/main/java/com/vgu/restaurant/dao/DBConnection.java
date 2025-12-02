package com.vgu.restaurant.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static final String ENV_FILE = "/.env";
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try (InputStream input = DBConnection.class.getResourceAsStream(ENV_FILE)) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                URL = prop.getProperty("DB_URL");
                USER = prop.getProperty("DB_USER");
                PASS = prop.getProperty("DB_PASSWORD");
            } else {
                System.out.println(".env file not found in resources!");
            }
        } catch (Exception e) {
            System.out.println("Error loading .env: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
            return null;
        }
    }
}