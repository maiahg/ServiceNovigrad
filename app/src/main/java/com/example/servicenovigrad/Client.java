package com.example.servicenovigrad;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client extends User implements Serializable {
    long requestID;
    DatabaseReference requestRef;
    Client() { }
    Client(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "Client");
    }

    public void submitRequest (Requests request) {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");
        Query lastQuery = requestRef.orderByKey().limitToLast(1);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long lastID = 0;

                if(snapshot.getChildrenCount() > 0) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        lastID = Long.parseLong(dataSnapshot.getKey());
                        break;
                    }

                    requestID = lastID + 1;
                } else {
                    requestID = 1;
                }

                requestRef.child(String.valueOf(requestID)).setValue(request);
                requestRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteRequest(String requestNumber) {
        requestRef = FirebaseDatabase.getInstance().getReference("requests").child(requestNumber);
        requestRef.getParent().child(requestNumber).removeValue();
    }
    public void rateBranch(String branchUserName, String newBranchRating, String newBranchRatingCount) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("branches").child(branchUserName);
        Map<String, Object> ratingUpdate = new HashMap<>();
        ratingUpdate.put("branchRating", newBranchRating);
        ratingUpdate.put("branchRatingCount", newBranchRatingCount);

        ref.updateChildren(ratingUpdate);
    }

    public boolean isNameValid(String name) {
        if(name.equals("N/A")) {
            return true;
        } else {
            String nameRegExpVar = "^[A-Za-z ]+$";
            Pattern pVar = Pattern.compile(nameRegExpVar);
            Matcher mVar = pVar.matcher(name);
            return mVar.matches();
        }
    }

    public boolean isAddressValid(String address) {
        if (address.equals("N/A")) {
            return true;
        } else {
            String specialCharacters = "!\"#$%^&*()_+=/*:;<>[]{}\\|~`";

            for (int i = 0; i < specialCharacters.length(); i++) {
                String specialCharacter = Character.toString(specialCharacters.charAt(i));

                if (address.contains(specialCharacter)) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean isPermitValid(String permit) {
        if(permit.equals("N/A")) {
            return true;
        } else {
            permit = permit.toLowerCase();
            if (!permit.equals("g1") || !permit.equals("g2") || permit.equals("g")) {
                return false;
            }
            return true;
        }
    }
}
