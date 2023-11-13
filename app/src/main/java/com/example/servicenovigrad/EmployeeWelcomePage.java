package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeWelcomePage extends AppCompatActivity {
    private Button signoutBtn;
    private TextView welcomeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        signoutBtn = findViewById(R.id.signoutE);
        welcomeTxt = findViewById(R.id.welcomeE);

        Intent intent = getIntent();

        welcomeTxt.setText("Bienvenue " + intent.getStringExtra("firstName") + "!");

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeWelcomePage.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(EmployeeWelcomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}