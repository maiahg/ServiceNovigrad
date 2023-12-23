package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientSubmittedRequests extends AppCompatActivity {
    DatabaseReference requestsRef, servicesRef;
    String dataBaseID, firstName, branchName, serviceName, customerName, customerLastName, customerAddress, customerDateOfBirth, customerPermit, requestStatus;
    boolean customerProofOfResidence, customerProofOfStatus, customerPhoto;
    RequestsList requestsAdapter;
    ListView requestsListView;
    ArrayList<String> requests;
    ImageButton closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_submitted_requests);

        Intent intent = getIntent();
        dataBaseID = intent.getStringExtra("dataBaseID");
        firstName = intent.getStringExtra("firstName");

        requestsRef = FirebaseDatabase.getInstance().getReference("requests");
        servicesRef = FirebaseDatabase.getInstance().getReference("services");
        requests = new ArrayList<>();

        closeBtn = findViewById(R.id.closeButton);
        requestsListView = findViewById(R.id.requestsList2);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientSubmittedRequests.this, ClientWelcomePage.class);
                intent.putExtra("dataBaseID", dataBaseID);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requests.clear();
                requestsAdapter = new RequestsList(ClientSubmittedRequests.this, requests);
                requestsListView.setAdapter(requestsAdapter);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("customerID").getValue().toString().equals(dataBaseID)) {
                        String requestNumber = postSnapshot.getKey();
                        requests.add('#' + requestNumber);
                    }
                }

                requestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String requestNumber = String.valueOf(requests.get(position));

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            if (postSnapshot.getKey().equals(requestNumber.split("#")[1])) {
                                serviceName = postSnapshot.child("serviceName").getValue().toString();
                                branchName = postSnapshot.child("branchName").getValue().toString();
                                customerName = postSnapshot.child("customerName").getValue().toString();
                                customerLastName = postSnapshot.child("customerLastName").getValue().toString();
                                customerDateOfBirth = postSnapshot.child("customerDateOfBirth").getValue().toString();
                                customerAddress = postSnapshot.child("customerAddress").getValue().toString();
                                customerPermit = postSnapshot.child("customerPermit").getValue().toString();
                                customerProofOfResidence = Boolean.parseBoolean(postSnapshot.child("proofOfResidenceAttached").getValue().toString());
                                customerProofOfStatus = Boolean.parseBoolean(postSnapshot.child("proofOfStatusAttached").getValue().toString());
                                customerPhoto = Boolean.parseBoolean(postSnapshot.child("photoAttached").getValue().toString());
                                requestStatus = postSnapshot.child("requestStatus").getValue().toString();
                            }
                        }
                        requestDetails(requestNumber);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void requestDetails(String requestNumber) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_client_request, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClientSubmittedRequests.this);
        dialogBuilder.setView(dialogView);

        Button closeBtn = dialogView.findViewById(R.id.closeVBtn);
        Button deleteBtn = dialogView.findViewById(R.id.deleteBtn);

        TextView requestName = dialogView.findViewById(R.id.requestName);
        TextView branchNameView = dialogView.findViewById(R.id.branchName);
        TextView statusTxt = dialogView.findViewById(R.id.statusTxt);
        TextView name = dialogView.findViewById(R.id.name);
        TextView lastName = dialogView.findViewById(R.id.lastName);
        TextView dob = dialogView.findViewById(R.id.dob);
        TextView address = dialogView.findViewById(R.id.address);
        TextView typeOfPermit = dialogView.findViewById(R.id.typeOfPermit);
        TextView proofOfResTxt = dialogView.findViewById(R.id.proofOfResTxt);
        TextView proofOfStatusTxt = dialogView.findViewById(R.id.proofOfStatusTxt);
        TextView photo = dialogView.findViewById(R.id.photo);

        boolean[] permitRequired = new boolean[1];
        boolean[] proofOfResidenceRequired = new boolean[1];
        boolean[] proofOfStatusRequired = new boolean[1];
        boolean[] photoRequired = new boolean[1];

        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(serviceName)) {
                        permitRequired[0] = Boolean.parseBoolean(postSnapshot.child("typeOfPermit").getValue().toString());
                        proofOfResidenceRequired[0] = Boolean.parseBoolean(postSnapshot.child("proofOfResidence").getValue().toString());
                        proofOfStatusRequired[0] = Boolean.parseBoolean(postSnapshot.child("proofOfStatus").getValue().toString());
                        photoRequired[0] = Boolean.parseBoolean(postSnapshot.child("photo").getValue().toString());
                        break;
                    }
                }

                requestName.setText(String.format(serviceName + "\nRequest " + requestNumber));
                statusTxt.setText(String.format("Request Status: " + requestStatus));
                branchNameView.setText(String.format("Branch name: " + branchName));
                name.setText(String.format("Name: " + customerName));
                lastName.setText(String.format("Last Name: " + customerLastName));
                dob.setText(String.format("Date of Birth: " + customerDateOfBirth));
                address.setText(String.format("Address: " + customerAddress));

                if(permitRequired[0]) {

                    if (!customerPermit.equals("N/A")) {
                        typeOfPermit.setText(String.format("License type: " + customerPermit));
                    } else {
                        typeOfPermit.setTextColor(Color.RED);
                        typeOfPermit.setText(String.format("License type: NOT SUBMITTED"));
                    }
                } else {
                    typeOfPermit.setText(String.format("License type: NOT REQUIRED"));
                }

                if (proofOfResidenceRequired[0]) {
                    if (customerProofOfResidence) {
                        proofOfResTxt.setText(String.format("Proof of Residency: SUBMITTED"));
                    } else {
                        proofOfResTxt.setTextColor(Color.RED);
                        proofOfResTxt.setText(String.format("Proof of Residency: NOT SUBMITTED"));
                    }
                } else {
                    proofOfResTxt.setText(String.format("Proof of Residency: NOT REQUIRED"));
                }

                if (proofOfStatusRequired[0]) {
                    if (customerProofOfStatus) {
                        proofOfStatusTxt.setText(String.format("Proof of Status: SUBMITTED"));
                    } else {
                        proofOfStatusTxt.setTextColor(Color.RED);
                        proofOfStatusTxt.setText(String.format("Proof of Status: NOT SUBMITTED"));
                    }
                } else {
                    proofOfStatusTxt.setText(String.format("Proof of Status: NOT REQUIRED"));
                }

                if (photoRequired[0]) {
                    if (customerPhoto) {
                        photo.setText(String.format("Your photo: SUBMITTED"));
                    } else {
                        photo.setTextColor(Color.RED);
                        photo.setText(String.format("Your photo: NOT SUBMITTED"));
                    }
                } else {
                    photo.setText(String.format("Your photo: NOT REQUIRED"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = new Client();
                String actualRequestNumber = requestNumber.split("#")[1];
                client.deleteRequest(actualRequestNumber);

                Toast.makeText(ClientSubmittedRequests.this, "Request deleted",
                        Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}