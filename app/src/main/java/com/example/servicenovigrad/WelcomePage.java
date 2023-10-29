package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class WelcomePage extends AppCompatActivity {
    Button logoutBtn;
    TextView textView;
    String firstName, userRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        logoutBtn = findViewById(R.id.logoutBtn);
        textView = findViewById(R.id.welcomText);
        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        userRole = intent.getStringExtra("role");

        textView.setText("Bienvenue " + firstName + "!\nVous êtes connect en tant que " + userRole);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(WelcomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            }
        });
    }

}