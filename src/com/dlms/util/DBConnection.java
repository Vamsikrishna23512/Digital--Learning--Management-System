package com.dlms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Updated connection string with all necessary parameters
    private static final String URL = "jdbc:mysql://localhost:3306/dlms_db?"
            + "useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true"
            + "&useUnicode=true"
            + "&characterEncoding=utf8";

    private static final String USER = "root";
    private static final String PASSWORD = "pawan21@";   // ←←← CHANGE THIS to your actual MySQL root password

    public static Connection getConnection() throws SQLException {
        try {
            // Optional: Force load the driver (sometimes needed)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}