package com.vgu.restaurant.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // KHAI BÁO THẲNG GIÁ TRỊ KẾT NỐI
    private static final String URL = "jdbc:mysql://localhost:3306/restaurantdb";
    private static final String USER = "root";
    private static final String PASS = "123456";

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
