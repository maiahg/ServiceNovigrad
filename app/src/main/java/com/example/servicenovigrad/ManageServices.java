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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageServices extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ListView servicesListView;
    private ArrayList<Service> services;
    private Button createBtn;
    private ImageButton homeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("services");

        servicesListView = findViewById(R.id.servicesListView);
        services = new ArrayList<>();

        createBtn = findViewById(R.id.createBtn);
        homeBtn = findViewById(R.id.homeSBtn);

        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                serviceDetails(service.getServiceName());
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addService();
           }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageServices.this, AdminWelcomePage.class);
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
                services.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    services.add(service);
                }

                ServiceList servicesAdapter = new ServiceList(ManageServices.this, services);
                servicesListView.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void serviceDetails(String serviceName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_edit_services, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Admin admin = new Admin();
        Button updateBtn = dialogView.findViewById(R.id.update);
        Button deleteBtn = dialogView.findViewById(R.id.delete);
        EditText editServiceName = dialogView.findViewById(R.id.editServiceName);
        CheckBox preNameEdit = dialogView.findViewById(R.id.prenameEdit);
        CheckBox nameEdit = dialogView.findViewById(R.id.nameEdit);
        CheckBox dobEdit = dialogView.findViewById(R.id.dobEdit);
        CheckBox addressEdit = dialogView.findViewById(R.id.addressEdit);
        CheckBox permitEdit = dialogView.findViewById(R.id.permitEdit);
        CheckBox residenceEdit = dialogView.findViewById(R.id.residenceEdit);
        CheckBox statusEdit = dialogView.findViewById(R.id.statusEdit);
        CheckBox photoEdit = dialogView.findViewById(R.id.photoEdit);

        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentServiceName = snapshot.child(serviceName).child("serviceName").getValue().toString();
                editServiceName.setText(currentServiceName);

                boolean preNameRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("preName").getValue().toString());
                boolean nameRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("name").getValue().toString());
                boolean dateOfBirthRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("dateOfBirth").getValue().toString());
                boolean addressRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("address").getValue().toString());
                boolean typeOfPermitRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("typeOfPermit").getValue().toString());
                boolean proofOfResidenceRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("proofOfResidence").getValue().toString());
                boolean proofOfStatusRequired = Boolean.parseBoolean(snapshot.child(serviceName).child("proofOfStatus").getValue().toString());
                boolean photo = Boolean.parseBoolean(snapshot.child(serviceName).child("photo").getValue().toString());

                if (preNameRequired) {
                    preNameEdit.setChecked(true);
                }
                if (nameRequired) {
                    nameEdit.setChecked(true);
                }
                if (dateOfBirthRequired) {
                    dobEdit.setChecked(true);
                }
                if (addressRequired) {
                    addressEdit.setChecked(true);
                }
                if (typeOfPermitRequired) {
                    permitEdit.setChecked(true);
                }
                if (proofOfResidenceRequired) {
                    residenceEdit.setChecked(true);
                }
                if (proofOfStatusRequired) {
                    statusEdit.setChecked(true);
                }
                if (photo) {
                    photoEdit.setChecked(true);
                }

                reference.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialog.show();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newServiceName = editServiceName.getText().toString().trim();

                if(newServiceName.isEmpty()) {
                    editServiceName.setError("Service name is required");
                } else {
                    admin.editService(serviceName, newServiceName, preNameEdit, nameEdit, dobEdit, addressEdit, permitEdit, residenceEdit, statusEdit, photoEdit);
                    dialog.dismiss();


                    Toast.makeText(ManageServices.this, "Service modified", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.deleteService(serviceName);
                dialog.dismiss();

                Toast.makeText(ManageServices.this, "Service deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addService() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_create_services, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Admin admin = new Admin();
        Button create = dialogView.findViewById(R.id.createService);
        EditText serviceNameEdt = dialogView.findViewById(R.id.addServiceName);
        CheckBox prenameBox = dialogView.findViewById(R.id.prenameBox);
        CheckBox nameBox = dialogView.findViewById(R.id.nameBox);
        CheckBox dobBox = dialogView.findViewById(R.id.dobBox);
        CheckBox addressBox = dialogView.findViewById(R.id.addressBox);
        CheckBox permitBox = dialogView.findViewById(R.id.permitBox);
        CheckBox residenceBox = dialogView.findViewById(R.id.residenceBox);
        CheckBox statusBox = dialogView.findViewById(R.id.statusBox);
        CheckBox photoBox = dialogView.findViewById(R.id.photoBox);

        dialogBuilder.setView(dialogView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        create.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               String serviceNameString = serviceNameEdt.getText().toString().trim();

               if (serviceNameString.isEmpty()) {

                   serviceNameEdt.setError("Service name required");
               } else {
                   admin.createService(serviceNameString, prenameBox,nameBox, dobBox, addressBox, permitBox, residenceBox, statusBox, photoBox);
                   dialog.dismiss();

                   Toast.makeText(ManageServices.this, "Service created", Toast.LENGTH_SHORT).show();
               }
           }
        });
    }
}