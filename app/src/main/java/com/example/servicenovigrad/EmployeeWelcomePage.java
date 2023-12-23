package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeWelcomePage extends AppCompatActivity {
    private Button signOutBtn, createBranchBtn, signInBtn;
    private TextView welcomeTxt;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        signOutBtn = findViewById(R.id.signoutE);
        welcomeTxt = findViewById(R.id.welcomeE);
        createBranchBtn = findViewById(R.id.createBranch);
        signInBtn = findViewById(R.id.connectBranch);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");

        welcomeTxt.setText(String.format("Welcome " + firstName + "!"));

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeWelcomePage.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(EmployeeWelcomePage.this, "You have logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        createBranchBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               createBranch();
           }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInBranch();
            }
        });

    }

    private void createBranch() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_employee_create_branch, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Employee employee = new Employee();
        Button createBranch = dialogView.findViewById(R.id.createBranchBtn);
        EditText branchUserName = dialogView.findViewById(R.id.createUserName);
        EditText branchPassword = dialogView.findViewById(R.id.createPassword);
        EditText branchName = dialogView.findViewById(R.id.createBranchName);
        EditText branchPhoneNumber = dialogView.findViewById(R.id.createPhone);
        EditText branchAddress = dialogView.findViewById(R.id.createAddress);

        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        createBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = branchUserName.getText().toString().trim();
                String password = branchPassword.getText().toString().trim();
                String name = branchName.getText().toString().trim();
                String phoneNumber = branchPhoneNumber.getText().toString().trim();
                String address = branchAddress.getText().toString().trim();

                // Empty error handling
                if (userName.isEmpty()) {
                    branchUserName.setError("Username required");
                    return;
                } if (password.isEmpty()) {
                    branchPassword.setError("Password required");
                    return;
                } if (name.isEmpty()) {
                    branchName.setError("Branch  required");
                    return;
                } if (phoneNumber.isEmpty()) {
                    branchPhoneNumber.setError("Phone number required");
                    return;
                } if (address.isEmpty()) {
                    branchAddress.setError("Address required");
                    return;
                }

                // Invalid handling
                if (!employee.isUserNameValid(userName)) {
                    branchUserName.setError("Invalid username");
                    return;
                } if (!employee.isNameAndAddressValid(name)) {
                    branchName.setError("Invalid branch name");
                    return;
                } if (!employee.isPhoneNumberValid(phoneNumber)) {
                    branchPhoneNumber.setError("Invalid phone number");
                    return;
                } if (!employee.isNameAndAddressValid(address)) {
                    branchAddress.setError("Invalid address");
                    return;
                }

                employee.createBranch(userName, password, name, phoneNumber, address);
                dialog.dismiss();

                Toast.makeText(EmployeeWelcomePage.this, "Branch account created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logInBranch() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_employee_login_branch, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Button logIn = dialogView.findViewById(R.id.signInBranch);
        EditText userName = dialogView.findViewById(R.id.userNameL);
        EditText password = dialogView.findViewById(R.id.passwordL);

        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branchUserName = userName.getText().toString();
                String branchPassword = password.getText().toString();

                // Empty error handling
                if (branchUserName.isEmpty()) {
                    userName.setError("Username required");
                } else if (branchPassword.isEmpty()) {
                    password.setError("Password required");
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("branches");
                    Query checkBranchDatabase = reference.orderByChild("branchUserName").equalTo(branchUserName);

                    checkBranchDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot branchSample : snapshot.getChildren()) {
                                    Branch branch = branchSample.getValue(Branch.class);

                                    if (branch.getBranchPassword().equals(branchPassword)) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(EmployeeWelcomePage.this, BranchHomePage.class);
                                        intent.putExtra("branchUserName", branch.getBranchUserName());
                                        intent.putExtra("branchName", branch.getBranchName());
                                        intent.putExtra("firstName", firstName);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        password.setError("Incorrect password");
                                    }
                                }
                            } else {
                                userName.setError("Account does not exist");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
    }


}