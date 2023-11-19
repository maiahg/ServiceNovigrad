package com.example.servicenovigrad;

import java.io.Serializable;

public class Client extends User implements Serializable {
    Client(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "Client");
    }
    Client() { }
}
