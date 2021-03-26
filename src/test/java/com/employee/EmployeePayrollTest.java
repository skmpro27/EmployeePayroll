package com.employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
