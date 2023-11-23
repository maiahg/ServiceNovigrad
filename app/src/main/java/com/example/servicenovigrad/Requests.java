package com.example.servicenovigrad;

public class Requests {
    String customerID, branchName, serviceName, customerName, customerLastName, customerDateOfBirth, customerAddress, customerPermit, requestStatus;
    boolean proofOfResidenceAttached, proofOfStatusAttached, photoAttached;

    public Requests() {}

    public Requests(String customerID, String branchName, String serviceName, String customerName, String customerLastName, String customerDateOfBirth, String customerAddress, String customerPermit, boolean proofOfResidenceAttached, boolean proofOfStatusAttached, boolean photoAttached, String requestStatus) {
        this.customerID = customerID;
        this.branchName = branchName;
        this.serviceName = serviceName;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerDateOfBirth = customerDateOfBirth;
        this.customerAddress = customerAddress;
        this.customerPermit = customerPermit;
        this.proofOfResidenceAttached = proofOfResidenceAttached;
        this.proofOfStatusAttached = proofOfStatusAttached;
        this.photoAttached = photoAttached;
        this.requestStatus = requestStatus;
    }
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerDateOfBirth() {
        return customerDateOfBirth;
    }

    public void setCustomerDateOfBirth(String customerDateOfBirth) {
        this.customerDateOfBirth = customerDateOfBirth;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPermit() {
        return customerPermit;
    }

    public void setCustomerPermit(String customerPermit) {
        this.customerPermit = customerPermit;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isProofOfResidenceAttached() {
        return proofOfResidenceAttached;
    }

    public void setProofOfResidenceAttached(boolean proofOfResidenceAttached) {
        this.proofOfResidenceAttached = proofOfResidenceAttached;
    }

    public boolean isProofOfStatusAttached() {
        return proofOfStatusAttached;
    }

    public void setProofOfStatusAttached(boolean proofOfStatusAttached) {
        this.proofOfStatusAttached = proofOfStatusAttached;
    }

    public boolean isPhotoAttached() {
        return photoAttached;
    }

    public void setPhotoAttached(boolean photoAttached) {
        this.photoAttached = photoAttached;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
