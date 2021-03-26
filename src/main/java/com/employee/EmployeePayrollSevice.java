package com.employee;

import java.sql.*;

class EmployeePayrollService {
    DatabaseConnection con = new DatabaseConnection();

    public String connect() throws ClassNotFoundException, SQLException {
        return con.connectionToDatabase();
    }

    public int dataInTable() throws SQLException, ClassNotFoundException {
        connect();
        PreparedStatement preparedStatement = con.connection.prepareStatement("SELECT * FROM employee_payroll");

        ResultSet resultSet = preparedStatement.executeQuery();
        int count = countResult(resultSet);
        con.connection.close();
        return count;
    }

    private int countResult(ResultSet resultSet) throws SQLException {
        int count = 0;
        while (resultSet.next()) {
            count++;
            System.out.println("Id: " + resultSet.getInt(1) +
                    "   Name: " + resultSet.getString(2) +
                    "   Gender: " + resultSet.getString(3) +
                    "   Address: " + resultSet.getString(4) +
                    "   Department: " + resultSet.getString(5) +
                    "   Phone: " + resultSet.getLong(6) +
                    "   Start: " + resultSet.getString(7));
        }
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

    public int dataInRange(Date start, Date end) throws SQLException, ClassNotFoundException {
        connect();
        PreparedStatement preparedStatement = con.connection.prepareStatement("SELECT * FROM employee_payroll WHERE start BETWEEN ? AND ?");
        preparedStatement.setDate(1, start);
        preparedStatement.setDate(2, end);

        ResultSet resultSet = preparedStatement.executeQuery();
        int count = countResult(resultSet);
        con.connection.close();
        return count;
    }
}
