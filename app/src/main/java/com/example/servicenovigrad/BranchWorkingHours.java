package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class BranchWorkingHours extends AppCompatActivity {
    CheckBox monBox, tuesBox, wedBox, thursBox, friBox, satBox, sunBox;
    ImageButton closeBtn;
    TextView currentHoursMon, currentHoursTues, currentHoursWed, currentHoursThurs, currentHoursFri, currentHoursSat, currentHoursSun, modifyMon, modifyTues, modifyWed, modifyThurs, modifyFri, modifySat, modifySun;
    DatabaseReference ref;
    String firstName, branchUserName, branchName, currentWorkingDays, currentWorkingHours, updatedWorkingDays;
    ArrayList<String> workingDaysList = new ArrayList<>();
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_working_hours);

        ref = FirebaseDatabase.getInstance().getReference("branches");

        monBox = findViewById(R.id.monBox);
        tuesBox = findViewById(R.id.tuesBox);
        wedBox = findViewById(R.id.wedBox);
        thursBox = findViewById(R.id.thursBox);
        friBox = findViewById(R.id.friBox);
        satBox = findViewById(R.id.satBox);
        sunBox = findViewById(R.id.sunBox);

        currentHoursMon = findViewById(R.id.currentHoursMon);
        currentHoursTues = findViewById(R.id.currentHoursTues);
        currentHoursWed = findViewById(R.id.currentHoursWed);
        currentHoursThurs = findViewById(R.id.currentHoursThurs);
        currentHoursFri = findViewById(R.id.currentHoursFri);
        currentHoursSat = findViewById(R.id.currentHoursSat);
        currentHoursSun = findViewById(R.id.currentHoursSun);

        modifyMon = findViewById(R.id.modifyMon);
        modifyTues = findViewById(R.id.modifyTues);
        modifyWed = findViewById(R.id.modifyWed);
        modifyThurs = findViewById(R.id.modifyThurs);
        modifyFri = findViewById(R.id.modifyFri);
        modifySat = findViewById(R.id.modifySat);
        modifySun = findViewById(R.id.modifySun);

        closeBtn = findViewById(R.id.closeHBtn);

        Intent intent = getIntent();
        branchUserName = intent.getStringExtra("branchUserName");
        branchName = intent.getStringExtra("branchName");
        firstName = intent.getStringExtra("firstName");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workingDaysList.clear();
                currentWorkingDays = snapshot.child(branchUserName).child("workingDays").getValue().toString();
                String[] workingDays = currentWorkingDays.split(", ");
                workingDaysList.addAll(Arrays.asList(workingDays));
                monBox.setChecked(Boolean.parseBoolean(workingDays[0]));
                tuesBox.setChecked(Boolean.parseBoolean(workingDays[1]));
                wedBox.setChecked(Boolean.parseBoolean(workingDays[2]));
                thursBox.setChecked(Boolean.parseBoolean(workingDays[3]));
                friBox.setChecked(Boolean.parseBoolean(workingDays[4]));
                satBox.setChecked(Boolean.parseBoolean(workingDays[5]));
                sunBox.setChecked(Boolean.parseBoolean(workingDays[6]));

                currentWorkingHours = snapshot.child(branchUserName).child("workingHours").getValue().toString();
                String[] workingHours = currentWorkingHours.split(", ");
                currentHoursMon.setText(String.format("Opening hours: " + workingHours[0]));
                currentHoursTues.setText(String.format("Opening hours: " + workingHours[1]));
                currentHoursWed.setText(String.format("Opening hours: " + workingHours[2]));
                currentHoursThurs.setText(String.format("Opening hours: " + workingHours[3]));
                currentHoursFri.setText(String.format("Opening hours: " + workingHours[4]));
                currentHoursSat.setText(String.format("Opening hours: " + workingHours[5]));
                currentHoursSun.setText(String.format("Opening hours: " + workingHours[6]));

                if(!monBox.isChecked()) {
                    modifyMon.setVisibility(View.INVISIBLE);
                    currentHoursMon.setText(String.format("Closed"));}
                else {modifyMon.setVisibility(View.VISIBLE);}

                if(!tuesBox.isChecked()) {
                    modifyTues.setVisibility(View.INVISIBLE);
                    currentHoursTues.setText(String.format("Closed"));}
                else {modifyTues.setVisibility(View.VISIBLE);}

                if(!wedBox.isChecked()) {
                    modifyWed.setVisibility(View.INVISIBLE);
                    currentHoursWed.setText(String.format("Closed"));}
                else {modifyWed.setVisibility(View.VISIBLE);}

                if(!thursBox.isChecked()) {
                    modifyThurs.setVisibility(View.INVISIBLE);
                    currentHoursThurs.setText(String.format("Closed"));}
                else {modifyThurs.setVisibility(View.VISIBLE);}

                if(!friBox.isChecked()) {
                    modifyFri.setVisibility(View.INVISIBLE);
                    currentHoursFri.setText(String.format("Closed"));}
                else {modifyFri.setVisibility(View.VISIBLE);}

                if(!satBox.isChecked()) {
                    modifySat.setVisibility(View.INVISIBLE);
                    currentHoursSat.setText(String.format("Closed"));}
                else {modifySat.setVisibility(View.VISIBLE);}

                if(!sunBox.isChecked()) {
                    modifySun.setVisibility(View.INVISIBLE);
                    currentHoursSun.setText(String.format("Closed"));}
                else {modifySun.setVisibility(View.VISIBLE);}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchHomePage.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish();
            }
        });

        monBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(0, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        tuesBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(1, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        wedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(2, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        thursBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(3, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        friBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(4, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        satBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(5, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        sunBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatedWorkingDays = getUpdatedWorkingDays(6, isChecked, workingDaysList);
                employee.updateWorkingDays(branchUserName, updatedWorkingDays);
            }
        });

        modifyMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 0);
                startActivity(intent);
                finish();
            }
        });

        modifyTues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 1);
                startActivity(intent);
                finish();
            }
        });

        modifyWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 2);
                startActivity(intent);
                finish();
            }
        });

        modifyThurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 3);
                startActivity(intent);
                finish();
            }
        });

        modifyFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 4);
                startActivity(intent);
                finish();
            }
        });

        modifySat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 5);
                startActivity(intent);
                finish();
            }
        });

        modifySun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchWorkingHours.this, BranchSetHours.class);
                intent.putExtra("branchUserName", branchUserName);
                intent.putExtra("branchName", branchName);
                intent.putExtra("firstName", firstName);
                intent.putExtra("dayToModify", 6);
                startActivity(intent);
                finish();
            }
        });

    }

    private String getUpdatedWorkingDays(int i, boolean b, ArrayList<String> workingDaysList) {
        String updatedValue = String.valueOf(b);
        workingDaysList.set(i, updatedValue);
        StringBuilder updatedWorkingDays = new StringBuilder();

        for(int j = 0; j < workingDaysList.size(); j++) {
            String dayStatus =  workingDaysList.get(j);
            if (j == 0) {
                updatedWorkingDays = new StringBuilder(dayStatus);
            } else {
                updatedWorkingDays.append(", ").append(dayStatus);
            }
        }
        return updatedWorkingDays.toString();
    }
}