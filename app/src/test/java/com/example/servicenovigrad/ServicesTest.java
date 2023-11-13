package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
public class ServicesTest {
    ArrayList<String> serviceInfo = new ArrayList<>();

    // Test the creation of services
    @Test
    public void createServices() {
        serviceInfo.add("Permis de conduire");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("false");
        serviceInfo.add("false");

        Service service = new Service("Permis de conduire", true, true, true, true, true, true, false, false);

        assertEquals(serviceInfo.get(0),service.getServiceName()); // Testing service name
        assertEquals(serviceInfo.get(1),String.valueOf(service.getPreName())); // Testing pre name requirement
        assertEquals(serviceInfo.get(2),String.valueOf(service.getName())); // Testing name requirement
        assertEquals(serviceInfo.get(3),String.valueOf(service.getDateOfBirth())); // Testing date of birth requirement
        assertEquals(serviceInfo.get(4),String.valueOf(service.getAddress())); // Testing address requirement
        assertEquals(serviceInfo.get(5),String.valueOf(service.getTypeOfPermit())); // Testing permit requirement
        assertEquals(serviceInfo.get(6),String.valueOf(service.getProofOfResidence())); // Testing proof of residence requirement
        assertEquals(serviceInfo.get(7),String.valueOf(service.getProofOfStatus())); // Testing proof of status requirement
        assertEquals(serviceInfo.get(8),String.valueOf(service.getPhoto())); // Testing photo requirement
        serviceInfo.clear();

    }

    @Test
    public void editServices() {
        serviceInfo.add("Permis de conduire");
        serviceInfo.add("true");
        serviceInfo.add("false");
        serviceInfo.add("true");
        serviceInfo.add("true");
        serviceInfo.add("false");
        serviceInfo.add("true");
        serviceInfo.add("false");
        serviceInfo.add("false");

        Service service = new Service("Permis de conduire", true, true, true, true, true, true, false, false);

        service.setName(false);
        service.setTypeOfPermit(false);

        assertEquals(serviceInfo.get(0),service.getServiceName()); // Testing service name
        assertEquals(serviceInfo.get(1),String.valueOf(service.getPreName())); // Testing pre name requirement
        assertEquals(serviceInfo.get(2),String.valueOf(service.getName())); // Testing name requirement
        assertEquals(serviceInfo.get(3),String.valueOf(service.getDateOfBirth())); // Testing date of birth requirement
        assertEquals(serviceInfo.get(4),String.valueOf(service.getAddress())); // Testing address requirement
        assertEquals(serviceInfo.get(5),String.valueOf(service.getTypeOfPermit())); // Testing permit requirement
        assertEquals(serviceInfo.get(6),String.valueOf(service.getProofOfResidence())); // Testing proof of residence requirement
        assertEquals(serviceInfo.get(7),String.valueOf(service.getProofOfStatus())); // Testing proof of status requirement
        assertEquals(serviceInfo.get(8),String.valueOf(service.getPhoto())); // Testing photo requirement
        serviceInfo.clear();
    }


}
