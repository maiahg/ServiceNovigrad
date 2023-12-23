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
import java.util.Arrays;

public class BranchServices extends AppCompatActivity {
    String firstName, branchUserName, branchName, currentBranchServices;
    Button addServiceBtn;
    ImageButton closeBtn;
    DatabaseReference branchesRef, servicesRef;
    ListView branchServicesList;
    ArrayList<String> services;
    ArrayList<Service> availableServices;
    BranchServicesList servicesAdapter;
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_services);

        branchesRef = FirebaseDatabase.getInstance().getReference("branches");
        servicesRef = FirebaseDatabase.getInstance().getReference("services");

        Intent intent = getIntent();
        branchUserName = intent.getStringExtra("branchUserName");
        branchName = intent.getStringExtra("branchName");
        firstName = intent.getStringExtra("firstName");

        branchServicesList = findViewById(R.id.servicesList);
        services = new ArrayList<>();
        availableServices = new ArrayList<>();

        addServiceBtn = findViewById(R.id.addServiceBtn);
        closeBtn = findViewById(R.id.closeSBtn);

        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchServices.this, BranchHomePage.class);
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

        branchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                if (snapshot.child(branchUserName).child("branchServices").exists()) {
                    currentBranchServices = snapshot.child(branchUserName).child("branchServices").getValue().toString();
                    String str[] = currentBranchServices.split(", ");
                    services.addAll(Arrays.asList(str));
                }

                servicesAdapter = new BranchServicesList(BranchServices.this, services);
                branchServicesList.setAdapter(servicesAdapter);

                branchServicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String serviceName = services.get(position);
                        deleteService(serviceName);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addService() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_employee_add_service, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        ListView servicesListView = dialogView.findViewById(R.id.servicesListView);
        Button updateBtn = dialogView.findViewById(R.id.updateService);

        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                availableServices.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    availableServices.add(service);
                }

                ServiceList availableServiceAdapter = new ServiceList(BranchServices.this, availableServices);
                servicesListView.setAdapter(availableServiceAdapter);

                servicesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                ArrayList<Service> servicesRemoved = new ArrayList<>();

                for(int i = 0; i < servicesListView.getCount(); i++) {
                    Service serviceItem = availableServiceAdapter.getItem(i);
                    String serviceName = serviceItem.getServiceName();

                    if(services.contains(serviceName)) {
                        servicesRemoved.add(serviceItem);
                    }
                }

                for (Service service : servicesRemoved) {
                    availableServiceAdapter.remove(service);
                }

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newBranchServices = null;

                        for (int i = 0; i < services.size(); i++) {
                            String serviceName = services.get(i);
                            if (i==0) {
                                newBranchServices = serviceName;
                            } else {
                                newBranchServices = newBranchServices + ", " + serviceName;
                            }
                        }
                        servicesAdapter.notifyDataSetChanged();
                        employee.updateServices(branchUserName, newBranchServices);
                        dialog.dismiss();
                        Toast.makeText(BranchServices.this, "Services added", Toast.LENGTH_SHORT).show();
                    }
                });

                servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        view.setSelected(!view.isSelected());

                        Service serviceItem = availableServiceAdapter.getItem(position);
                        String serviceName = serviceItem.getServiceName();

                        if (servicesListView.isItemChecked(position) && !services.contains(serviceName)) {
                            services.add(serviceName);
                        } else {
                            services.remove(serviceName);
                        }
                    }
                });
                servicesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void deleteService(String serviceName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_employee_delete_services,null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        Button confirmBtn = dialogView.findViewById(R.id.ouiBtn);
        Button cancelBtn = dialogView.findViewById(R.id.nonBtn);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBranchServices = null;

                services.remove(serviceName);
                if (services.isEmpty()) {
                    newBranchServices = null;
                } else {
                    for(int i = 0; i < services.size(); i++) {
                        String serviceName = services.get(i);
                        if(i == 0) {
                            newBranchServices = serviceName;
                        } else {
                            newBranchServices = newBranchServices + ", " + serviceName;
                        }
                    }
                }

                servicesAdapter.notifyDataSetChanged();
                employee.updateServices(branchUserName, newBranchServices);
                dialog.dismiss();
                Toast.makeText(BranchServices.this, "Service deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}