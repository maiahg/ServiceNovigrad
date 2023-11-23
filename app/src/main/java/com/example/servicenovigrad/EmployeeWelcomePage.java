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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        signOutBtn = findViewById(R.id.signoutE);
        welcomeTxt = findViewById(R.id.welcomeE);
        createBranchBtn = findViewById(R.id.createBranch);
        signInBtn = findViewById(R.id.connectBranch);

        Intent intent = getIntent();

        welcomeTxt.setText("Bienvenue " + intent.getStringExtra("firstName") + "!");

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeWelcomePage.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(EmployeeWelcomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
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
                String userName = branchUserName.getText().toString();
                String password = branchPassword.getText().toString();
                String name = branchName.getText().toString();
                String phoneNumber = branchPhoneNumber.getText().toString();
                String address = branchAddress.getText().toString();

                // Empty error handling
                if (userName.isEmpty()) {
                    branchUserName.setError("Nom d'utilisateur requis");
                    return;
                } if (password.isEmpty()) {
                    branchPassword.setError("Mot de passe requis");
                    return;
                } if (name.isEmpty()) {
                    branchName.setError("Nom de la succursale requis");
                    return;
                } if (phoneNumber.isEmpty()) {
                    branchPhoneNumber.setError("Numéro de téléphone requis");
                    return;
                } if (address.isEmpty()) {
                    branchAddress.setError("Adresse requise");
                    return;
                }

                // Invalid handling
                if (!isNameValid(userName)) {
                    branchUserName.setError("Nom d'utilisateur invalid");
                    return;
                } if (!isNameValid(name)) {
                    branchName.setError("Nom de la succursale invalid");
                    return;
                } if (!isPhoneNumberValid(phoneNumber)) {
                    branchPhoneNumber.setError("Numéro de téléphone invalid");
                    return;
                } if (!isAddressValid(address)) {
                    branchAddress.setError("Adresse invalide");
                    return;
                }

                employee.createBranch(userName, password, name, phoneNumber, address);
                dialog.dismiss();

                Toast.makeText(EmployeeWelcomePage.this, "Compte pour la succursale crée", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNameValid(String userName) {
        String specialCharacters = "!\"#$%^&*()_+-=/*:;<>[]{}\\|~`";

        for (int i = 0; i < specialCharacters.length(); i++) {
            String specialCharacter = Character.toString(specialCharacters.charAt(i));

            if (userName.contains(specialCharacter)) {
                return false;
            }
        }
        return true;
    }
    private boolean isAddressValid(String userName) {
        String specialCharacters = "!\"#$%^&*()_+=/*:;<>[]{}\\|~`";

        for (int i = 0; i < specialCharacters.length(); i++) {
            String specialCharacter = Character.toString(specialCharacters.charAt(i));

            if (userName.contains(specialCharacter)) {
                return false;
            }
        }
        return true;
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegExpVar = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        Pattern pVar = Pattern.compile(phoneRegExpVar);
        Matcher mVar = pVar.matcher(phoneNumber);
        return mVar.matches();
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
                    userName.setError("Nom d'utilisateur requis");
                } else if (branchPassword.isEmpty()) {
                    password.setError("Mot de passe requis");
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
                                        intent.putExtra("branchPassword", branch.getBranchPassword());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        password.setError("Mot de passe incorrect");
                                    }
                                }
                            } else {
                                userName.setError("Le compte n'existe pas");
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