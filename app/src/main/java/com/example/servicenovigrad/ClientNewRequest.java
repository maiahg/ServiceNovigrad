package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientNewRequest extends AppCompatActivity {
    private DatabaseReference branchesRef, serviceRef;
    private String dataBaseID, firstName;
    private EditText branchNameSearch, branchAddressSearch;
    private Button extraFilterBtn, searchBtn;
    private ImageButton closeBtn;
    private ArrayList<Service> services;
    private ArrayList<Object> extraFilter = new ArrayList<>();
    private ArrayList<Branch> branches = new ArrayList<>();
    private ListView branchesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_new_request);

        Intent intent = getIntent();
        dataBaseID = intent.getStringExtra("dataBaseID");
        firstName = intent.getStringExtra("firstName");

        branchesRef = FirebaseDatabase.getInstance().getReference("branches");
        serviceRef = FirebaseDatabase.getInstance().getReference("services");
        services = new ArrayList<>();

        branchNameSearch = findViewById(R.id.branchNameEdt);
        branchAddressSearch = findViewById(R.id.branchAddressEdt);
        extraFilterBtn = findViewById(R.id.extraFilterBtn);
        searchBtn = findViewById(R.id.searchBtn);
        closeBtn = findViewById(R.id.closeR);
        branchesListView = findViewById(R.id.branchesListView);

        for(int i = 0; i < 10; i++) {
            if (i < 7) {
                extraFilter.add(false);
            } else {
                extraFilter.add("");
            }
        }

        branchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = branches.get(position);
                branchesDetails(branch.getBranchName());
            }
        });

        extraFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExtraFilter();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branches.clear();
                branchesListView.setAdapter(null);
                search();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientNewRequest.this, ClientWelcomePage.class);
                intent.putExtra("dataBaseID", dataBaseID);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void branchesDetails(String branchName) {
        branchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if(postSnapshot.child("branchName").getValue().toString().equals(branchName)) {
                        Intent intent = new Intent(ClientNewRequest.this, ClientBranchInfo.class);
                        intent.putExtra("dataBaseID", dataBaseID);
                        intent.putExtra("branchName", postSnapshot.getKey());

                        startActivity(intent);
                        branchesRef.removeEventListener(this);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getExtraFilter() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_customer_extra_filter, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        ListView servicesListView = dialogView.findViewById(R.id.servicesList2);
        CheckBox monBox = dialogView.findViewById(R.id.mondayBox);
        CheckBox tuesBox = dialogView.findViewById(R.id.tuesdayBox);
        CheckBox wedBox = dialogView.findViewById(R.id.wednesdayBox);
        CheckBox thursBox = dialogView.findViewById(R.id.thursdayBox);
        CheckBox friBox = dialogView.findViewById(R.id.fridayBox);
        CheckBox satBox = dialogView.findViewById(R.id.saturdayBox);
        CheckBox sunBox = dialogView.findViewById(R.id.sundayBox);
        EditText serviceNameEdt = dialogView.findViewById(R.id.serviceNameEdt);
        Button filterBtn = dialogView.findViewById(R.id.filterBtn);
        ArrayList<CheckBox> array = new ArrayList<>();

        array.add(monBox);
        array.add(tuesBox);
        array.add(wedBox);
        array.add(thursBox);
        array.add(friBox);
        array.add(satBox);
        array.add(sunBox);

        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        for(int i = 0; i < extraFilter.size(); i++) {
            if(i < 8) {
                switch (i) {
                    case 0:
                        if(extraFilter.get(i).equals(true)) {
                            monBox.setChecked(true);
                        }
                        break;

                    case 1:
                        if(extraFilter.get(i).equals(true)) {
                            tuesBox.setChecked(true);
                        }
                        break;

                    case 2:
                        if(extraFilter.get(i).equals(true)) {
                            wedBox.setChecked(true);
                        }
                        break;

                    case 3:
                        if(extraFilter.get(i).equals(true)) {
                            thursBox.setChecked(true);
                        }
                        break;

                    case 4:
                        if(extraFilter.get(i).equals(true)) {
                            friBox.setChecked(true);
                        }
                        break;

                    case 5:
                        if(extraFilter.get(i).equals(true)) {
                            satBox.setChecked(true);
                        }
                        break;

                    case 6:
                        if(extraFilter.get(i).equals(true)) {
                            sunBox.setChecked(true);
                        }
                        break;

                    case 7:
                        serviceNameEdt.setText(extraFilter.get(i).toString());
                        break;
                }
            }
        }

        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                ServiceList servicesAdapter = new ServiceList(ClientNewRequest.this, services);
                servicesListView.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = serviceNameEdt.getText().toString().trim();

                for (int i = 0; i < array.size(); i++) {
                    CheckBox box = array.get(i);
                    extraFilter.set(i, box.isChecked());
                }

                extraFilter.set(7, serviceName);
                dialog.dismiss();
            }
        });

    }

    private void search() {
        branchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String branchName = branchNameSearch.getText().toString().toLowerCase().trim().length() > 0 ? branchNameSearch.getText().toString().toLowerCase().trim() : "";
                String branchAddress = branchAddressSearch.getText().toString().toLowerCase().trim().length() > 0 ? branchAddressSearch.getText().toString().toLowerCase().trim() : "";
                extraFilter.set(8, branchName);
                extraFilter.set(9, branchAddress);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    String workingDays = branch.getWorkingDays();
                    String[] workingDaysList = workingDays.split(", ");

                    boolean[] defaultFilter = {extraFilter.get(0).equals(true), extraFilter.get(1).equals(true), extraFilter.get(2).equals(true), extraFilter.get(3).equals(true), extraFilter.get(4).equals(true), extraFilter.get(5).equals(true), extraFilter.get(6).equals(true), !extraFilter.get(7).equals(""), !extraFilter.get(8).equals(""), !extraFilter.get(9).equals("")};

                    boolean[] branchFilter = {false, false, false, false, false, false, false, false, false, false};

                    for (int i = 0; i < extraFilter.size(); i++) {
                        switch (i) {
                            case 0:
                                if(extraFilter.get(0).equals(true)) {
                                    boolean monday = Boolean.parseBoolean(workingDaysList[0]);
                                    if (monday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 1:
                                if(extraFilter.get(1).equals(true)) {
                                    boolean tuesday = Boolean.parseBoolean(workingDaysList[1]);
                                    if (tuesday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 2:
                                if(extraFilter.get(2).equals(true)) {
                                    boolean wednesday = Boolean.parseBoolean(workingDaysList[2]);
                                    if (wednesday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 3:
                                if(extraFilter.get(3).equals(true)) {
                                    boolean thursday = Boolean.parseBoolean(workingDaysList[3]);
                                    if (thursday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 4:
                                if(extraFilter.get(4).equals(true)) {
                                    boolean friday = Boolean.parseBoolean(workingDaysList[4]);
                                    if (friday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 5:
                                if(extraFilter.get(5).equals(true)) {
                                    boolean saturday = Boolean.parseBoolean(workingDaysList[5]);
                                    if (saturday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 6:
                                if(extraFilter.get(6).equals(true)) {
                                    boolean sunday = Boolean.parseBoolean(workingDaysList[6]);
                                    if (sunday) {
                                        branchFilter[i] = true;
                                        break;
                                    } else {
                                        branchFilter[i] = false;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 7:
                                if (!extraFilter.get(7).equals("")) {
                                    if (!postSnapshot.child("branchServices").getValue().toString().toLowerCase().contains(extraFilter.get(7).toString().toLowerCase())) {
                                        branchFilter[i] = false;
                                        break;
                                    } else {
                                        branchFilter[i] = true;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 8:
                                if (!extraFilter.get(8).equals("")) {
                                    if (!postSnapshot.child("branchName").getValue().toString().toLowerCase().equals(extraFilter.get(8).toString().toLowerCase())) {
                                        branchFilter[i] = false;
                                        break;
                                    } else {
                                        branchFilter[i] = true;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }

                            case 9:
                                if (!extraFilter.get(9).equals("")) {
                                    if (!postSnapshot.child("branchAddress").getValue().toString().toLowerCase().equals(extraFilter.get(9).toString().toLowerCase())) {
                                        branchFilter[i] = false;
                                        break;
                                    } else {
                                        branchFilter[i] = true;
                                        break;
                                    }
                                } else {
                                    branchFilter[i] = false;
                                    break;
                                }
                        }
                    }

                    if (Arrays.equals(defaultFilter, branchFilter)) {
                        branches.add(branch);
                    }
                }

                BranchList branchesAdapter = new BranchList(ClientNewRequest.this, branches);
                branchesListView.setAdapter(branchesAdapter);
                branchesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        branchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branches.clear();;

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    branches.add(branch);
                }

                BranchList branchesAdapter = new BranchList(ClientNewRequest.this, branches);
                branchesListView.setAdapter(branchesAdapter);
                branchesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}