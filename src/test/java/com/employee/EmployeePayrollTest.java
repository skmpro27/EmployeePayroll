package com.employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeePayrollTest {
    EmployeePayrollService employeePayroll;

    @Before
    public void setUp() throws Exception {
        employeePayroll = new EmployeePayrollService();
    }

    @Test
    public void givenDatabaseSetupConnectionReturnConnected() {
        try {
            String result = employeePayroll.connect();
            Assert.assertEquals("Connected..", result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseRetrieveDataInTable() {
        try {
            int result = employeePayroll.dataInTable();
            Assert.assertEquals(4, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseUpdateDataInTableUsingPreparedStatement() {
        try {
            int result = employeePayroll.updateTableBasicPay(2000000, "Manju");
            Assert.assertEquals(1, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseRetrieveDataByNameUsingPreparedStatement() {
        try {
            int result = employeePayroll.retrieveByName("Manju");
            Assert.assertEquals(2000000, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseRetrieveDataInRangeOfDate() {
        try {
            int result = employeePayroll.dataInRange(Date.valueOf("2019-01-01"), Date.valueOf("2020-11-01"));
            Assert.assertEquals(1, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseGroupByGenderReturnSum() {
        try {
            int result = employeePayroll.sumGroupBy("M","Gender");
            Assert.assertEquals(900000, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDatabaseGroupByGenderReturnAverage() {
        try {
            int result = employeePayroll.averageGroupBy("M","Gender");
            Assert.assertEquals(450000, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addNewEmployeeToNewPayroll() {
        try {
            int result = employeePayroll.addNewEmployee("Shubha", "F", "Bhopal",
                    78696278, Date.valueOf("2020-02-01"), 4000000);
            Assert.assertEquals(2, result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
