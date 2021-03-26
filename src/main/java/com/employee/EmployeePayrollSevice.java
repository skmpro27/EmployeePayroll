package com.employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class EmployeePayrollService {
    DatabaseConnection con = new DatabaseConnection();

    public String connect() throws ClassNotFoundException, SQLException {
        return con.connectionToDatabase();
    }

    public int dataInTable() throws SQLException, ClassNotFoundException {
        connect();
        Statement statement = con.connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM employee_payroll");
        int count = 0;
        while (resultSet.next()) {
            count++;
            System.out.println(resultSet.getString(1) +
                               " Name: " + resultSet.getString(2) +
                               " Date " + resultSet.getString(3) +
                               " Salary " + resultSet.getString(4));
        }
        con.connection.close();
        return count;
    }

    public int updateTable() throws SQLException, ClassNotFoundException {
        connect();
        Statement statement = con.connection.createStatement();
        int resultSet = statement.executeUpdate( "UPDATE employee_payroll " +
                                                        "SET Basic_Pay = 3000000 " +
                                                        "WHERE Name = 'Yashi'");
        con.connection.close();
        return resultSet;
    }
}
