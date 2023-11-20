package com.example.servicenovigrad;

import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Employee extends User implements Serializable {
    Employee() {}
    Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "Employé");
    }

    public void createBranch(String branchUserName, String branchPassword, String branchName, String branchPhoneNumber, String branchAddress) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("branches");

        Branch branch = new Branch (branchUserName, branchPassword, branchName, branchPhoneNumber, branchAddress);

        reference.child(branchName).setValue(branch);

    }
}
