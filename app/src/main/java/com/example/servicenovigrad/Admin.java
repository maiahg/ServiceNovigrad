package com.example.servicenovigrad;

import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    public Admin() {
        super("Admin", "Admin", "admin@gmail.com", "123admin456", "Admin" );
    }

    public void createService(String serviceName, CheckBox preName, CheckBox name, CheckBox dateOfBirth, CheckBox address, CheckBox typeOfPermit, CheckBox proofOfResidence, CheckBox proofOfStatus, CheckBox photo) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("services");

        Service service = new Service (serviceName, preName.isChecked(), name.isChecked(), dateOfBirth.isChecked(), address.isChecked(),
                typeOfPermit.isChecked(), proofOfResidence.isChecked(), proofOfStatus.isChecked(),
               photo.isChecked());

        reference.child(serviceName).setValue(service);

    }

    public void editService(String serviceName, String newServiceName, CheckBox preName, CheckBox name, CheckBox dateOfBirth, CheckBox address, CheckBox typeOfPermit, CheckBox proofOfResidence, CheckBox proofOfStatus, CheckBox photo) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("services").child(serviceName);

        Service service = new Service (newServiceName, preName.isChecked(), name.isChecked(), dateOfBirth.isChecked(), address.isChecked(),
                typeOfPermit.isChecked(), proofOfResidence.isChecked(), proofOfStatus.isChecked(),
                photo.isChecked());

        reference.removeValue();
        reference.getParent().child(newServiceName).setValue(service);
    }

    public void deleteService(String serviceName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("services").child(serviceName);
        reference.getParent().child(serviceName).removeValue();
    }

    public void deleteAccount(String dataBaseID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(dataBaseID);
        reference.getParent().child(dataBaseID).removeValue();
    }
}
