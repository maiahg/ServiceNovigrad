package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageAccounts extends AppCompatActivity {
    private ListView accountsListView;
    private ArrayList<User> userList;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ImageButton homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);

        accountsListView = findViewById(R.id.accountsListView);
        userList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        homeBtn = findViewById(R.id.homeABtn);

    accountsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            User users = userList.get(i);
            deleteAccount(users);
        }
    });

    homeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageAccounts.this, AdminWelcomePage.class);
            startActivity(intent);
            finish();
        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }

                AccountList accountList = new AccountList(ManageAccounts.this, userList);
                accountsListView.setAdapter(accountList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteAccount(User user) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_delete_accounts, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Button confirmBtn = dialogView.findViewById(R.id.yes);
        Button cancelBtn = dialogView.findViewById(R.id.cancel);
        Admin admin = new Admin();

        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("Admin")) {
                    dialog.dismiss();
                    Toast.makeText(ManageAccounts.this, "Admin account cannot be deleted!",Toast.LENGTH_SHORT).show();
                } else {
                    admin.deleteAccount(user.getDataBaseID());
                    dialog.dismiss();

                    Toast.makeText(ManageAccounts.this, "Account deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}