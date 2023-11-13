package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class CreateAccountsTest {
    ArrayList<String> userInfo = new ArrayList<>();

    // Test the creation of an employee account
    @Test
    public void createEmployeeAccount() {
        userInfo.add("Bob");
        userInfo.add("James");
        userInfo.add("bjames@gmail.com");
        userInfo.add("123Test456");
        userInfo.add("Employé");

        User employee = new Employee("Bob", "James", "bjames@gmail.com", "123Test456");
        assertEquals(userInfo.get(0),employee.getFirstName()); // Testing first name
        assertEquals(userInfo.get(1),employee.getLastName()); // Testing last name
        assertEquals(userInfo.get(2),employee.getEmail()); // Testing email
        assertEquals(userInfo.get(3),employee.getPassword()); // Testing password
        assertEquals(userInfo.get(4),employee.getRole()); // Testing role
        userInfo.clear();
    }

    // Test the creation of a client account
    @Test
    public void createClientAccount() {
        userInfo.add("Jane");
        userInfo.add("Doe");
        userInfo.add("jdoe@gmail.com");
        userInfo.add("123456");
        userInfo.add("Client");

        User client = new Client("Jane", "Doe", "jdoe@gmail.com", "123456");
        assertEquals(userInfo.get(0),client.getFirstName()); // Testing first name
        assertEquals(userInfo.get(1),client.getLastName()); // Testing last name
        assertEquals(userInfo.get(2),client.getEmail()); // Testing email
        assertEquals(userInfo.get(3),client.getPassword()); // Testing password
        assertEquals(userInfo.get(4),client.getRole()); // Testing role
        userInfo.clear();
    }

    // Test the creation of an admin account
    @Test
    public void createAdminAccount() {
        userInfo.add("Admin");
        userInfo.add("Admin");
        userInfo.add("admin@gmail.com");
        userInfo.add("123admin456");
        userInfo.add("Admin");

        User admin = new Admin();
        assertEquals(userInfo.get(0),admin.getFirstName()); // Testing first name
        assertEquals(userInfo.get(1),admin.getLastName()); // Testing last name
        assertEquals(userInfo.get(2),admin.getEmail()); // Testing email
        assertEquals(userInfo.get(3),admin.getPassword()); // Testing password
        assertEquals(userInfo.get(4),admin.getRole()); // Testing role
        userInfo.clear();
    }
}
