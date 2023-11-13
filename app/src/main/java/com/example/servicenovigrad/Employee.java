package com.example.servicenovigrad;

import java.io.Serializable;

public class Employee extends User implements Serializable {
    public Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "Employé");
    }
    public Employee() {}
}
