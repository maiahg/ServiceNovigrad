package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ClientBranchInfo extends AppCompatActivity {
    private DatabaseReference branchRef, servicesRef;
    private String dataBaseID, firstName, branchUserName, branchName, branchAddress, branchPhoneNumber, branchServices, branchRating, branchRatingCount, workingHours, workingDays, servicesTxt;
    private TextView branchNameView, branchAddressView, branchPhoneNumberView, branchServicesView, mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, saturdayHours, sundayHours;
    private Button sendRequestBtn, rateBranchBtn;
    private ImageButton closeBtn;
    private RatingBar ratingBar;
    private final int RES_CODE = 1000;
    private final int STATUS_CODE = 2000;
    private final int PHOTO_CODE = 3000;
    boolean resProofAttached = false;
    boolean statusProofAttached = false;
    boolean photoProofAttached = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_branch_info);

        Intent intent = getIntent();
        dataBaseID = intent.getStringExtra("dataBaseID");
        firstName = intent.getStringExtra("firstName");
        branchUserName = intent.getStringExtra("branchUserName");

        branchRef = FirebaseDatabase.getInstance().getReference("branches");

        branchNameView = findViewById(R.id.branchNameView);
        branchAddressView = findViewById(R.id.addressTxt);
        branchPhoneNumberView = findViewById(R.id.phoneTxt);
        branchServicesView = findViewById(R.id.servicesTxt);
        mondayHours = findViewById(R.id.mondayTxt);
        tuesdayHours = findViewById(R.id.tuesdayTxt);
        wednesdayHours = findViewById(R.id.wednesdayTxt);
        thursdayHours = findViewById(R.id.thursdayTxt);
        fridayHours = findViewById(R.id.fridayTxt);
        saturdayHours = findViewById(R.id.saturdayTxt);
        sundayHours = findViewById(R.id.sundayTxt);
        sendRequestBtn = findViewById(R.id.sendRequestBtn);
        rateBranchBtn = findViewById(R.id.rateBtn);
        closeBtn = findViewById(R.id.closeB);
        ratingBar = findViewById(R.id.ratingBar);

        branchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchName = snapshot.child(branchUserName).child("branchName").getValue().toString();
                branchAddress = snapshot.child(branchUserName).child("branchAddress").getValue().toString();
                branchPhoneNumber = snapshot.child(branchUserName).child("branchPhoneNumber").getValue().toString();
                branchRating = snapshot.child(branchUserName).child("branchRating").getValue().toString();

                if (snapshot.child(branchUserName).child("branchServices").getValue() == null) {
                    branchServicesView.setText("N/A");
                } else {
                    branchServices = snapshot.child(branchUserName).child("branchServices").getValue().toString();
                    String[] branchServicesArray = branchServices.split(", ");
                    for(int i = 0; i < branchServicesArray.length; i++) {
                        if(i == 0) {
                            servicesTxt = branchServicesArray[0];
                        } else {
                            servicesTxt = servicesTxt + "\n" + branchServicesArray[i];
                        }
                    }

                    branchServicesView.setText(servicesTxt);
                }

                branchRatingCount = snapshot.child(branchUserName).child("branchRatingCount").getValue().toString();

                workingDays = snapshot.child(branchUserName).child("workingDays").getValue().toString();
                String[] workingDaysArray = workingDays.split(", ");

                workingHours = snapshot.child(branchUserName).child("workingHours").getValue().toString();
                String[] workingHoursArray = workingHours.split(", ");

                branchNameView.setText(branchName);
                branchAddressView.setText(branchAddress);
                branchPhoneNumberView.setText(branchPhoneNumber);
                ratingBar.setRating(Float.parseFloat(branchRating));

                if (Objects.equals(workingDaysArray[0], "true")) {
                    mondayHours.setText(String.format("Monday: " + workingHoursArray[0]));
                } else {
                    mondayHours.setText("Monday: Closed");
                }

                if (Objects.equals(workingDaysArray[1], "true")) {
                    tuesdayHours.setText(String.format("Tuesday: " + workingHoursArray[1]));
                } else {
                    tuesdayHours.setText("Tuesday: Closed");
                }

                if (Objects.equals(workingDaysArray[2], "true")) {
                    wednesdayHours.setText(String.format("Wednesday: " + workingHoursArray[2]));
                } else {
                    wednesdayHours.setText("Wednesday: Closed");
                }

                if (Objects.equals(workingDaysArray[3], "true")) {
                    thursdayHours.setText(String.format("Thursday: " + workingHoursArray[3]));
                } else {
                    thursdayHours.setText("Thursday: Closed");
                }

                if (Objects.equals(workingDaysArray[4], "true")) {
                    fridayHours.setText(String.format("Friday: " + workingHoursArray[4]));
                } else {
                    fridayHours.setText("Friday: Closed");
                }

                if (Objects.equals(workingDaysArray[5], "true")) {
                    saturdayHours.setText(String.format("Saturday: " + workingHoursArray[5]));
                } else {
                    saturdayHours.setText("Saturday: Closed");
                }

                if (Objects.equals(workingDaysArray[6], "true")) {
                    sundayHours.setText(String.format("Sunday: " + workingHoursArray[6]));
                } else {
                    sundayHours.setText("Sunday: Closed");
                }

                branchRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientBranchInfo.this, ClientNewRequest.class);
                intent.putExtra("dataBaseID", dataBaseID);
                intent.putExtra("firstName", firstName);
                intent.putExtra("branchUserName", branchUserName);
                startActivity(intent);
                finish();
            }
        });

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchServicesView.getText().toString() == "N/A") {
                    Toast.makeText(ClientBranchInfo.this, "Cannot send requests to this branch", Toast.LENGTH_SHORT).show();
                } else {
                    openRequestForm();
                }
            }
        });

        rateBranchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateBranch();
            }
        });

    }

    private void openRequestForm() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_client_request_form, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        servicesRef = FirebaseDatabase.getInstance().getReference("services");
        Client client = new Client();

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        Spinner servicesSpinner = dialogView.findViewById(R.id.servicesSpinner);
        EditText lastName = dialogView.findViewById(R.id.lastNameEdt);
        EditText name = dialogView.findViewById(R.id.nameEdt);
        EditText dob = dialogView.findViewById(R.id.dobEdt);
        EditText address = dialogView.findViewById(R.id.addressEdt);
        EditText permit = dialogView.findViewById(R.id.permitType);
        TextView resProof = dialogView.findViewById(R.id.resProof);
        TextView statusProof = dialogView.findViewById(R.id.statusProof);
        TextView photoTxt = dialogView.findViewById(R.id.photoTxt);
        TextView uploadRes = dialogView.findViewById(R.id.uploadRes);
        TextView uploadStatus = dialogView.findViewById(R.id.uploadStatus);
        TextView uploadPhoto = dialogView.findViewById(R.id.uploadPhoto);
        Button submitRequestBtn = dialogView.findViewById(R.id.submitRequestBtn);

        String[] serviceName = new String[1];
        boolean[] lastNameRequired = new boolean[1];
        boolean[] nameRequired = new boolean[1];
        boolean[] dobRequired = new boolean[1];
        boolean[] addressRequired = new boolean[1];
        boolean[] permitRequired = new boolean[1];
        boolean[] proofOfResidenceRequired = new boolean[1];
        boolean[] proofOfStatusRequired = new boolean[1];
        boolean[] photoRequired = new boolean[1];

        ArrayList<String> servicesArrayList;

        if(!branchServices.isEmpty()) {
            servicesArrayList = new ArrayList<>(Arrays.asList(branchServices.split(", ")));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ClientBranchInfo.this, android.R.layout.simple_spinner_item, servicesArrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            servicesSpinner.setAdapter(spinnerAdapter);
        }

        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceName[0] = servicesSpinner.getSelectedItem().toString().trim();

                servicesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lastNameRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("preName").getValue().toString());
                        nameRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("name").getValue().toString());
                        dobRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("dateOfBirth").getValue().toString());
                        addressRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("address").getValue().toString());
                        permitRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("typeOfPermit").getValue().toString());
                        proofOfResidenceRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("proofOfResidence").getValue().toString());
                        proofOfStatusRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("proofOfStatus").getValue().toString());
                        photoRequired[0] = Boolean.parseBoolean(snapshot.child(serviceName[0]).child("photo").getValue().toString());

                        if(lastNameRequired[0]) {
                            lastName.setHint("Last Name (required)");
                        } else {
                            lastName.setHint("Last Name (not required)");
                        }

                        if(nameRequired[0]) {
                            name.setHint("Name (required)");
                        } else {
                            name.setHint("Name (not required)");
                        }

                        if(dobRequired[0]) {
                            dob.setHint("Date of Birth (required)");
                        } else {
                            dob.setHint("Date of Birth (not required)");
                        }

                        if(addressRequired[0]) {
                            address.setHint("Address (required)");
                        } else {
                            address.setHint("Address (not required)");
                        }

                        if(permitRequired[0]) {
                            permit.setHint("License type (required)");
                        } else {
                            permit.setHint("License type (not required)");
                        }

                        if(proofOfResidenceRequired[0]) {
                            resProof.setText("Proof of Residency (required)");
                        } else {
                            resProof.setText("Proof of Residency (not required)");
                        }

                        if(proofOfStatusRequired[0]) {
                            statusProof.setText("Proof of Status (required)");
                        } else {
                            statusProof.setText("Proof of Status (not required)");
                        }

                        if(photoRequired[0]) {
                            photoTxt.setText("Your photo (required)");
                        } else {
                            photoTxt.setText("Your photo (not required)");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RES_CODE);
                resProofAttached = true;
            }
        });

        uploadStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, STATUS_CODE);
                statusProofAttached = true;
            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PHOTO_CODE);
                photoProofAttached = true;
            }
        });

        submitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = name.getText().toString();
                String customerLastName = lastName.getText().toString();
                String customerDateOfBirth = dob.getText().toString();
                String customerAddress = address.getText().toString();
                String customerPermit = permit.getText().toString();

                // Empty handling
                if(nameRequired[0]) {
                    if(customerName.isEmpty()) {
                        name.setError("Your name is required");
                        return;
                    }
                } else {
                    customerName = "N/A";
                }

                if(lastNameRequired[0]) {
                    if(customerLastName.isEmpty()) {
                        lastName.setError("Your last name is required");
                        return;
                    }
                } else {
                    customerLastName = "N/A";
                }

                if(dobRequired[0]) {
                    if(customerDateOfBirth.isEmpty()) {
                        dob.setError("Your date of birth is required");
                        return;
                    }
                } else {
                    customerDateOfBirth = "N/A";
                }

                if(addressRequired[0]) {
                    if(customerAddress.isEmpty()) {
                        address.setError("Your address is required");
                        return;
                    }
                } else {
                    customerAddress = "N/A";
                }

                if(permitRequired[0]) {
                    if(customerPermit.isEmpty()) {
                        permit.setError("Your license type is required");
                        return;
                    }
                } else {
                    customerPermit = "N/A";
                }

                // Invalid handling
                if (!client.isNameValid(customerName)) {
                    name.setError("Invalid");
                    return;
                } if(!client.isNameValid(customerLastName)) {
                    lastName.setError("Invalid");
                    return;
                } if(!client.isAddressValid(customerAddress)) {
                    address.setError("Invalid");
                    return;
                } if(!client.isPermitValid(customerPermit)) {
                    permit.setError("Invalid");
                    return;
                }

                Requests requests = new Requests(dataBaseID, branchName, serviceName[0],customerName, customerLastName, customerDateOfBirth, customerAddress, customerPermit, resProofAttached, statusProofAttached, photoProofAttached, "Pending");

                client.submitRequest(requests);
                Toast.makeText(ClientBranchInfo.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
    }

    private void rateBranch() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_client_rate_branch, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Button submitReviewBtn = dialogView.findViewById(R.id.submitReview);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar2);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        submitReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float userRating = ratingBar.getRating();
                float currentBranchRating = Float.parseFloat(branchRating);
                long currentRatingCount = Long.parseLong(branchRatingCount);

                float newBranchRating = Float.parseFloat(String.valueOf(Math.round((((currentBranchRating * currentRatingCount) + userRating)/(currentRatingCount + 1)) * 100)/100.0));
                long newBranchRatingCount = currentRatingCount + 1;

                Client client = new Client();
                client.rateBranch(branchUserName, String.valueOf(newBranchRating), String.valueOf(newBranchRatingCount));
                Toast.makeText(ClientBranchInfo.this, "Review Submitted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}