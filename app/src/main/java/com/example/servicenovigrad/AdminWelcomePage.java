package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminWelcomePage extends AppCompatActivity {
    private Button logOutBtn, servicesBtn, accountsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_page);

        logOutBtn = findViewById(R.id.signoutA);
        servicesBtn = findViewById(R.id.servicesBtn);
        accountsBtn = findViewById(R.id.accountsBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(AdminWelcomePage.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminWelcomePage.this, "You have logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(AdminWelcomePage.this, ManageServices.class);
                startActivity(intent);
                finish();
            }
        });

        accountsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(AdminWelcomePage.this, ManageAccounts.class);
                startActivity(intent);
                finish();
            }
        });

    }
}