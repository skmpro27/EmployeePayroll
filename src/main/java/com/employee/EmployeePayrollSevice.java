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

    public int updateTableBasicPay(int basicPay, String name) throws SQLException, ClassNotFoundException {
        connect();
        PreparedStatement preparedStatement = con.connection.prepareStatement("UPDATE employee_payroll SET Basic_Pay = ? WHERE Name = ?");
        preparedStatement.setInt(1, basicPay);
        preparedStatement.setString(2, name);

        int resultSet = preparedStatement.executeUpdate();
        con.connection.close();
        return resultSet;
    }

    public int retrieveByName(String name) throws SQLException, ClassNotFoundException {
        connect();
        int basicPay = 0;
        PreparedStatement preparedStatement = con.connection.prepareStatement("SELECT * FROM employee_payroll WHERE Name = ?");
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            basicPay = resultSet.getInt(8);
        }
        con.connection.close();
        return basicPay;
    }
}
