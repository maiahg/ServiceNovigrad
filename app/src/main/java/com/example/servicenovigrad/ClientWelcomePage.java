package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClientWelcomePage extends AppCompatActivity {
    private Button signoutBtn;
    private TextView welcomeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_welcome_page);

        signoutBtn = findViewById(R.id.signoutC);
        welcomeTxt = findViewById(R.id.welcomeC);
        Intent intent = getIntent();

        welcomeTxt.setText(String.format("Bienvenue " + intent.getStringExtra("firstName") + "!"));

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ClientWelcomePage.this, Login.class);
               startActivity(intent);
               finish();
               Toast.makeText(ClientWelcomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}