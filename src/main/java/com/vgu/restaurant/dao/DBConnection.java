package com.vgu.restaurant.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASS");

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