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

public class BranchRequests extends AppCompatActivity {
    ImageButton closeBtn;
    String firstName, branchUserName, branchName, serviceName, customerID, customerName, customerLastName, customerAddress, customerDateOfBirth, customerPermit, requestStatus;
    boolean customerProofOfResidence, customerProofOfStatus, customerPhoto;
    DatabaseReference requestsRef, servicesRef;
    ListView requestsListView;
    ArrayList<String> requests;
    RequestsList requestsAdapter;
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_requests);

        Intent intent = getIntent();
        branchUserName = intent.getStringExtra("branchUserName");
        branchName = intent.getStringExtra("branchName");
        firstName = intent.getStringExtra("firstName");


        closeBtn = findViewById(R.id.closeRBtn);

        requestsRef = FirebaseDatabase.getInstance().getReference("requests");
        servicesRef = FirebaseDatabase.getInstance().getReference("services");

        requestsListView = findViewById(R.id.requestsListView);
        requests = new ArrayList<>();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequests.this, BranchHomePage.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
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
                requestsAdapter = new RequestsList(BranchRequests.this, requests);
                requestsListView.setAdapter(requestsAdapter);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if(postSnapshot.child("branchName").getValue().equals(branchName)) {
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
                                customerID = postSnapshot.child("customerID").getValue().toString();
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
        View dialogView = inflater.inflate(R.layout.dialog_branch_request, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BranchRequests.this);
        dialogBuilder.setView(dialogView);

        Button approveBtn = dialogView.findViewById(R.id.approveBtn);
        Button rejectBtn = dialogView.findViewById(R.id.rejectBtn);

        TextView serviceRequestTxt = dialogView.findViewById(R.id.serviceRequestTxt);
        TextView requestStatusTxt = dialogView.findViewById(R.id.requestStatus);
        TextView serviceNameTxt = dialogView.findViewById(R.id.customerName);
        TextView serviceLastName = dialogView.findViewById(R.id.customerLastName);
        TextView serviceDOB = dialogView.findViewById(R.id.customerDOB);
        TextView serviceAddress = dialogView.findViewById(R.id.customerAddress);
        TextView servicePermit = dialogView.findViewById(R.id.customerPermit);
        TextView proofOfRes = dialogView.findViewById(R.id.proofOfRes);
        TextView proofOfStatus = dialogView.findViewById(R.id.proofOfStatus);
        TextView photoProof = dialogView.findViewById(R.id.photoProof);

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

                serviceRequestTxt.setText(String.format(serviceName + "\nRequest " + requestNumber));
                requestStatusTxt.setText(String.format("Request Status: " + requestStatus));
                serviceNameTxt.setText(String.format("First Name: " + customerName));
                serviceLastName.setText(String.format("Last Name: " + customerLastName));
                serviceDOB.setText(String.format("Date of birth: " + customerDateOfBirth));
                serviceAddress.setText(String.format("Address: " + customerAddress));

                if(permitRequired[0]) {

                    if (!customerPermit.equals("N/A")) {
                        servicePermit.setText(String.format("License type: " + customerPermit));
                    } else {
                        servicePermit.setTextColor(Color.RED);
                        servicePermit.setText(String.format("License type: NOT SUBMITTED"));
                    }
                } else {
                    servicePermit.setText(String.format("License type: NOT REQUIRED"));
                }

                if (proofOfResidenceRequired[0]) {
                    if (customerProofOfResidence) {
                        proofOfRes.setText(String.format("Proof of Residency: SUBMITTED"));
                    } else {
                        proofOfRes.setTextColor(Color.RED);
                        proofOfRes.setText(String.format("Proof of Residency: NOT SUBMITTED"));
                    }
                } else {
                    proofOfRes.setText(String.format("Proof of Residency: NOT REQUIRED"));
                }

                if (proofOfStatusRequired[0]) {
                    if (customerProofOfStatus) {
                        proofOfStatus.setText(String.format("Proof of Status: SUBMITTED"));
                    } else {
                        proofOfStatus.setTextColor(Color.RED);
                        proofOfStatus.setText(String.format("Proof of Status: NOT SUBMITTED"));
                    }
                } else {
                    proofOfStatus.setText(String.format("Proof of Status: NOT REQUIRED"));
                }

                if (photoRequired[0]) {
                    if (customerPhoto) {
                        photoProof.setText(String.format("Client's photo: SUBMITTED"));
                    } else {
                        photoProof.setTextColor(Color.RED);
                        photoProof.setText(String.format("Client's photo: NOT SUBMITTED"));
                    }
                } else {
                    photoProof.setText(String.format("Client's photo: NOT REQUIRED"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actualRequestNumber = requestNumber.split("#")[1];

                employee.approveRequest(actualRequestNumber);
                Toast.makeText(BranchRequests.this, "Request Approved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actualRequestNumber = requestNumber.split("#")[1];

                employee.rejectRequest(actualRequestNumber);
                Toast.makeText(BranchRequests.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}