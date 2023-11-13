package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText editFirstName, editLastName, editEmail, editPassword;
    private String firstNameString, lastNameString, emailString, passwordString, role;
    private Spinner rolePicker;
    private Button registerBtn;
    private TextView textView;
    private FirebaseDatabase dataBase;
    private DatabaseReference reference;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFirstName = findViewById(R.id.nameTxt);
        editLastName = findViewById(R.id.surnameTxt);
        editEmail = findViewById(R.id.emailTxt);
        editPassword = findViewById(R.id.passwordTxt);
        rolePicker = findViewById(R.id.rolePicker);
        registerBtn = findViewById(R.id.registerBtn);
        textView = findViewById(R.id.loginNow);


        // the role picker drop down menu
        rolePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Veuillez sélectionner un type d'utilisateur", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameString = editFirstName.getText().toString();
                lastNameString = editLastName.getText().toString();
                emailString = editEmail.getText().toString();
                passwordString = editPassword.getText().toString();
                dataBase = FirebaseDatabase.getInstance();
                reference = dataBase.getReference("users");


                // Empty error
                if (firstNameString.isEmpty() || lastNameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() ) {
                    Toast.makeText(Register.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if email is valid
                if (!isEmailValid(emailString)) {
                    Toast.makeText(Register.this, "Email invalide", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if first name and last is valid
                if (!isNameValid(firstNameString) || !isNameValid(lastNameString) ) {
                    Toast.makeText(Register.this, "Nom ou Prénoms invalides", Toast.LENGTH_SHORT).show();
                    return;
                }

               switch(role) {
                   case "Client":
                       user = new Client(firstNameString, lastNameString, emailString, passwordString);
                       break;

                   case "Employé":
                       user = new Employee(firstNameString, lastNameString, emailString, passwordString);
                       break;
               }

                DatabaseReference newNode = reference.push();
                String nodeKey = newNode.getKey();
                user.setDataBaseID(nodeKey);
                newNode.setValue(user);

                Toast.makeText(Register.this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);

            }


        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean isEmailValid(String email) {
        // valid character
        String emailRegExpVar = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pVar = Pattern.compile(emailRegExpVar);
        Matcher mVar = pVar.matcher(email);
        if (!mVar.matches()) {
            return false;
        }

        // check if there is only one "@" and at least one "."
        int atIndex = email.indexOf("@");
        int dotIndex = email.indexOf(".");

        if (atIndex == -1 || dotIndex == -1 || email.indexOf("@", atIndex + 1) != -1) {
            return false;
        }

        // Check if there is at least one character before "@" and after "."
        if (atIndex == 0 || dotIndex <= atIndex + 1 || dotIndex == email.length() - 1) {
            return false;
        }

        // Check if there are two dots in the domain part
        int secondDotIndex = email.indexOf(".", dotIndex+1);
        if (secondDotIndex != -1 && secondDotIndex != email.lastIndexOf(".")) {
            return false;
        }

        return true;
    }

    private boolean isNameValid(String name) {
        String nameRegExpVar = "^[A-Za-z ]+$";
        Pattern pVar = Pattern.compile(nameRegExpVar);
        Matcher mVar = pVar.matcher(name);
        return mVar.matches();

    }
}