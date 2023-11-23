package com.example.servicenovigrad;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmployeeUnitTest {
    /* Test if username is valid
    In this test, the username is not valid
     */
    @Test
    public void isUserNameValidTest() {
        Employee employee = new Employee();
        boolean result = employee.isUserNameValid("Ottawa!!!");
        assertEquals(result, false);
    }

    /* Test if address is valid
    In this test, the address is valid
     */
    @Test
    public void isNameAndAddressValidTest() {
        Employee employee = new Employee();
        boolean result = employee.isNameAndAddressValid("123 Laurier St");
        assertEquals(result, true);
    }

}
