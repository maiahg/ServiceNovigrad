package com.example.servicenovigrad;

import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Employee extends User implements Serializable {
    Employee() {}
    Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "Employé");
    }

    public void createBranch(String branchUserName, String branchPassword, String branchName, String branchPhoneNumber, String branchAddress) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("branches");

        Branch branch = new Branch (branchUserName, branchPassword, branchName, branchPhoneNumber, branchAddress);
        reference.child(branchUserName).setValue(branch);

        DatabaseReference openingRef = reference.child(branchUserName);
        Map<String, Object> openingUpdate = new HashMap<>();
        openingUpdate.put("workingDays", "false, false, false, false, false, false, false");
        openingUpdate.put("workingHours", "N/A, N/A, N/A, N/A, N/A, N/A, N/A");

        openingRef.updateChildren(openingUpdate);


    }

    public void modifyBranchProfile(String branchUserName, String branchPassword, String newBranchName, String newBranchPhoneNumber, String newBranchAddress) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);

        Branch branch = new Branch(branchUserName, branchPassword, newBranchName, newBranchPhoneNumber, newBranchAddress);

        reference.removeValue();
        reference.getParent().child(branchUserName).setValue(branch);
    }

    public void updateServices(String branchUserName, String branchServices) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);
        Map<String, Object> servicesUpdate = new HashMap<>();
        servicesUpdate.put("branchServices", branchServices);

        ref.updateChildren(servicesUpdate);
    }

    public void updateWorkingDays(String branchUserName, String updatedWorkingDays) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);
        Map<String, Object> workingDaysUpdate = new HashMap<>();
        workingDaysUpdate.put("workingDays", updatedWorkingDays);

        ref.updateChildren(workingDaysUpdate);
    }

    public void updateWorkingHours(String branchUserName, String updatedWorkingHours) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);
        Map<String, Object> workingHoursUpdate = new HashMap<>();
        workingHoursUpdate.put("workingHours", updatedWorkingHours);

        ref.updateChildren(workingHoursUpdate);
    }
}
