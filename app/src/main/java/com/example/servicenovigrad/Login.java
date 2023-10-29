package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Objects;


public class Login extends AppCompatActivity {
    EditText editEmail, editPassword;
    String emailString, passwordString;
    Button btnLogin;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.emailAddressTxt);
        editPassword = findViewById(R.id.enterPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textView = findViewById(R.id.registerNow);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = editEmail.getText().toString();
                passwordString = editPassword.getText().toString();

                // Empty error
                if (emailString.isEmpty() || passwordString.isEmpty() ) {
                    Toast.makeText(Login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkUser();
                }

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkUser() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(email);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSample : snapshot.getChildren()) {
                        User user = userSample.getValue(User.class);

                        if (user.getPassword().equals(password)) {
                            Intent intent = new Intent(Login.this, WelcomePage.class);
                            intent.putExtra("firstName",user.getFirstName());
                            intent.putExtra("role", user.getRole());
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Le compte n'existe pas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}