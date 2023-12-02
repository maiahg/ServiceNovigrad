package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestTest {
    Requests request = new Requests("-abcdefgh", "Laurier", "Permis de conduire", "Jane", "Doe", "20/11/1994", "123 Laurier", "G1", true, false, false, "en attente");

    @Test
    public void customerIDTest() {
        assertEquals("-abcdefgh", request.getCustomerID());
    }

    @Test
    public void branchNameTest() {
        assertEquals("Laurier", request.getBranchName());
    }

    @Test
    public void serviceNameTest() {
        assertEquals("Permis de conduire", request.getServiceName());
    }

    @Test
    public void proofOfResidenceTest() {
        assertTrue(request.isProofOfResidenceAttached());
    }

    @Test public void proofOfStatusTest() {
        assertFalse(request.isProofOfStatusAttached());
    }
}
