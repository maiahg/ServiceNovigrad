package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClientWelcomePage extends AppCompatActivity {
    private Button signOutBtn, newRequestBtn, submittedRequestsBtn;
    private TextView welcomeTxt;
    private String dataBaseID, firstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_welcome_page);

        signOutBtn = findViewById(R.id.signoutC);
        newRequestBtn = findViewById(R.id.newRequestBtn);
        submittedRequestsBtn = findViewById(R.id.submittedRequestsBtn);
        welcomeTxt = findViewById(R.id.welcomeC);
        Intent intent = getIntent();

        dataBaseID = intent.getStringExtra("dataBaseID");
        firstName = intent.getStringExtra("firstName");
        welcomeTxt.setText(String.format("Bienvenue " + firstName + "!"));

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ClientWelcomePage.this, Login.class);
               startActivity(intent);
               finish();
               Toast.makeText(ClientWelcomePage.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            }
        });

        newRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientWelcomePage.this, ClientNewRequest.class);
                intent.putExtra("dataBaseID", dataBaseID);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });

        submittedRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientWelcomePage.this, ClientSubmittedRequests.class);
                intent.putExtra("dataBaseID", dataBaseID);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });


    }
}