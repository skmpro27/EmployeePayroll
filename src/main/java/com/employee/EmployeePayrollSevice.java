package com.employee;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

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

    public int groupByToPerformOperations(String sql, String field, String column) throws SQLException, ClassNotFoundException {
        connect();
        int sum = 0;
        PreparedStatement preparedStatement = con.connection.prepareStatement(sql);

        preparedStatement.setString(1, field);
        preparedStatement.setString(2, column);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            sum = resultSet.getInt(1);
        }
        return sum;
    }

    public int sumGroupBy(String field, String column) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(Basic_Pay) FROM employee_payroll WHERE gender = ? GROUP BY ?";
        return groupByToPerformOperations(sql, field, column);
    }

    public int averageGroupBy(String field, String column) throws SQLException, ClassNotFoundException {
        String sql = "SELECT AVG(Basic_Pay) FROM employee_payroll WHERE gender = ? GROUP BY ?";
        return groupByToPerformOperations(sql, field, column);
    }

    public int addNewEmployee (String name, String gender, String address, long phone, Date date, double salary) throws SQLException, ClassNotFoundException {
        connect();
        int result = 0;
        con.connection.setAutoCommit(false);
        try {
            try {
                PreparedStatement preparedStatement = con.connection.prepareStatement(
                        "INSERT INTO payroll ( Salary, Deductions, Taxable_Pay, Income_Tax, Net_Pay ) " +
                                "VALUES ( ?, ?, ?, ?, ? );");
                preparedStatement.setDouble(1, salary);
                preparedStatement.setDouble(2, salary * 0.20);
                preparedStatement.setDouble(3, salary - salary * 0.20);
                preparedStatement.setDouble(4, (salary - salary * 0.20) * 0.1);
                preparedStatement.setDouble(5, salary - (salary - salary * 0.20) * 0.1);
                result += preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("work on Already existing salary");
            }

            PreparedStatement preparedStatement2 = con.connection.prepareStatement(
                    "INSERT INTO employee_payroll ( Name, Gender, Address, Phone, Start, Salary) " +
                            "VALUES ( ?, ?, ?, ?, ?, ? );");
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2, gender);
            preparedStatement2.setString(3, address);
            preparedStatement2.setLong(4, phone);
            preparedStatement2.setDate(5, date);
            preparedStatement2.setDouble(6, salary);
            result += preparedStatement2.executeUpdate();
            con.connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            con.connection.rollback();
        }
        con.connection.close();
        return result;
    }
}
