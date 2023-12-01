package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class BranchSetHours extends AppCompatActivity {
    Button setOpeningTime, setClosingTime, updateBtn;
    ImageButton closeBtn;
    int openingHour, closingHour, openingMinute, closingMinute;
    String firstName, branchUserName, branchName, currentWorkingHours, updatedWorkingHours;
    int dayToModify;
    DatabaseReference ref;
    ArrayList<String> workingHoursList = new ArrayList<>();
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_set_hours);

        setOpeningTime = findViewById(R.id.openingBtn);
        setClosingTime = findViewById(R.id.closingBtn);
        closeBtn = findViewById(R.id.closeTBtn);
        updateBtn = findViewById(R.id.updateHoursBtn);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        branchUserName = intent.getStringExtra("branchUserName");
        branchName = intent.getStringExtra("branchName");
        dayToModify = intent.getIntExtra("dayToModify", 0);

        ref = FirebaseDatabase.getInstance().getReference("branches");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workingHoursList.clear();
                currentWorkingHours = snapshot.child(branchUserName).child("workingHours").getValue().toString();
                String[] workingHours = currentWorkingHours.split(", ");
                workingHoursList.addAll(Arrays.asList(workingHours));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchSetHours.this, BranchWorkingHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });

        setOpeningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        openingHour = hourOfDay;
                        openingMinute = minute;
                        setOpeningTime.setText(String.format(Locale.CANADA, "%02d:%02d", openingHour, openingMinute));
                    }
                };

                int style = AlertDialog.THEME_HOLO_LIGHT;
                TimePickerDialog timePickerDialog = new TimePickerDialog(BranchSetHours.this, style, onTimeSetListener, openingHour, openingMinute, true);

                timePickerDialog.setTitle("Sélectionner l'heure d'ouverture");
                timePickerDialog.show();
            }
        });

        setClosingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        closingHour = hourOfDay;
                        closingMinute = minute;
                        setClosingTime.setText(String.format(Locale.CANADA, "%02d:%02d", closingHour, closingMinute));
                    }
                };
                int style = AlertDialog.THEME_HOLO_LIGHT;
                TimePickerDialog timePickerDialog = new TimePickerDialog(BranchSetHours.this, style, onTimeSetListener, closingHour, closingMinute, true);

                timePickerDialog.setTitle("Sélectionner l'heure de fermeture");
                timePickerDialog.show();
            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newOpeningTime = String.format(Locale.CANADA, "%02d:%02d", openingHour, openingMinute);
                String newClosingTime = String.format(Locale.CANADA, "%02d:%02d", closingHour, closingMinute);
                String newWorkingHours = (newOpeningTime + " - " + newClosingTime);
                updatedWorkingHours = getUpdatedWorkingHours(dayToModify, newWorkingHours, workingHoursList);
                employee.updateWorkingHours(branchUserName, updatedWorkingHours);

                Intent intent = new Intent(BranchSetHours.this, BranchWorkingHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();

                Toast.makeText(BranchSetHours.this, "Les heures de travail mis à jour", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUpdatedWorkingHours(int i, String newWorkingHours, ArrayList<String> workingHoursList) {
        workingHoursList.set(i, newWorkingHours);
        StringBuilder updatedWorkingHours = new StringBuilder();

        for(int j = 0; j < workingHoursList.size(); j++) {
            String hoursStatus =  workingHoursList.get(j);
            if (j == 0) {
                updatedWorkingHours = new StringBuilder(hoursStatus);
            } else {
                updatedWorkingHours.append(", ").append(hoursStatus);
            }
        }
        return updatedWorkingHours.toString();
    }
}