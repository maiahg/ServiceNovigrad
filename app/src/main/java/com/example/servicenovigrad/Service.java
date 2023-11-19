package com.example.servicenovigrad;

public class Service {
    private String serviceName;
    boolean preName, name, dateOfBirth,  address,  typeOfPermit,  proofOfResidence,  proofOfStatus, photo;

    public Service() {}

    Service(String serviceName, boolean preName, boolean name, boolean dateOfBirth, boolean address, boolean typeOfPermit, boolean proofOfResidence, boolean proofOfStatus, boolean photo) {
        this.serviceName = serviceName;
        this.preName = preName;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.typeOfPermit = typeOfPermit;
        this.proofOfResidence = proofOfResidence;
        this.proofOfStatus = proofOfStatus;
        this.photo = photo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean getPreName() {
        return preName;
    }

    public void setPreName(boolean preName) {
        this.preName = preName;
    }

    public boolean getName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(boolean dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getAddress() {
        return address;
    }

    public void setAddress(boolean address) {
        this.address = address;
    }

    public boolean getTypeOfPermit() {
        return typeOfPermit;
    }

    public void setTypeOfPermit(boolean typeOfPermit) {
        this.typeOfPermit = typeOfPermit;
    }

    public boolean getProofOfResidence() {
        return proofOfResidence;
    }

    public void setProofOfResidence(boolean proofOfResidence) {
        this.proofOfResidence = proofOfResidence;
    }

    public boolean getProofOfStatus() {
        return proofOfStatus;
    }

    public void setProofOfStatus(boolean proofOfStatus) {
        this.proofOfStatus = proofOfStatus;
    }

    public boolean getPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }
}
