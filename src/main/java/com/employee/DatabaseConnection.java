package com.employee;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeepayroll?useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Skm@1234";

    String status;
    Connection connection;

    public String connectionToDatabase() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            status = "Driver is loaded";
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            status = "Connected..";
        } catch (Exception e) {
            System.out.println("Not connected");
        }
        return status;
    }
}
