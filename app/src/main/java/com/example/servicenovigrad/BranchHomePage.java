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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BranchHomePage extends AppCompatActivity {
    private Button modifyProfileBtn, modifyServiceBtn, modifyHoursBtn, viewRequestsBtn, logOutBtn;
    private DatabaseReference reference;
    private String branchUserName;
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_home_page);

        modifyProfileBtn = findViewById(R.id.modifyProfileBtn);
        modifyServiceBtn = findViewById(R.id.modifyServiceBtn);
        modifyHoursBtn = findViewById(R.id.modifyWorkingHoursBtn);
        viewRequestsBtn = findViewById(R.id.viewRequestBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

        Intent intent = getIntent();
        branchUserName = intent.getStringExtra("branchUserName");
        reference = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchHomePage.this, EmployeeWelcomePage.class);
                startActivity(intent);
                finish();
                Toast.makeText(BranchHomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            }
        });

        modifyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyProfile();
            }
        });

        modifyServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchHomePage.this, BranchServices.class);
                intent.putExtra("branchUserName", branchUserName);
                startActivity(intent);
                finish();
            }
        });

        modifyHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchHomePage.this, BranchWorkingHours.class);
                intent.putExtra("branchUserName", branchUserName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void modifyProfile() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_employee_modify_branch_profile,null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        EditText modifyBranchName = dialogView.findViewById(R.id.modifyBranchName);
        EditText modifyBranchNumber = dialogView.findViewById(R.id.modifyBranchNumber);
        EditText modifyBranchAddress = dialogView.findViewById(R.id.modifyBranchAddress);

        Button updateBtn = dialogView.findViewById(R.id.updateBtn);

        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Branch currentBranch = snapshot.getValue(Branch.class);

                modifyBranchName.setText(currentBranch.getBranchName());
                modifyBranchNumber.setText(currentBranch.getBranchPhoneNumber());
                modifyBranchAddress.setText(currentBranch.getBranchAddress());

                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialog.show();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBranchName = modifyBranchName.getText().toString();
                String newBranchNumber = modifyBranchNumber.getText().toString();
                String newBranchAddress = modifyBranchAddress.getText().toString();

                // Empty error handling
                if (newBranchName.isEmpty()) {
                    modifyBranchName.setError("Nom de la succursale requis");
                    return;
                } if (newBranchNumber.isEmpty()) {
                    modifyBranchNumber.setError("Numéro de téléphone requis");
                    return;
                } if (newBranchAddress.isEmpty()) {
                    modifyBranchAddress.setError("Adresse requise");
                    return;
                }

                // Invalid handling
                if (!employee.isNameAndAddressValid(newBranchName)) {
                    modifyBranchName.setError("Nom de la succursale invalid");
                    return;
                } if (!employee.isPhoneNumberValid(newBranchNumber)) {
                    modifyBranchNumber.setError("Numéro de téléphone invalid");
                    return;
                } if (!employee.isNameAndAddressValid(newBranchAddress)) {
                    modifyBranchAddress.setError("Adrese invalide");
                    return;
                }

                Employee employee = new Employee();
                employee.modifyBranchProfile(branchUserName, newBranchName, newBranchNumber, newBranchAddress);

                dialog.dismiss();
                Toast.makeText(BranchHomePage.this, "Profil de la succursale modifié", Toast.LENGTH_SHORT).show();

            }
        });

    }
}