package com.example.servicenovigrad;

public class Branch {
    String branchUserName, branchPassword, branchName, branchAddress, branchPhoneNumber, branchServices, openingDays, openingHours;
    public Branch(){}
    public Branch(String branchUserName, String branchPassword, String branchName, String branchPhoneNumber, String branchAddress) {
        this.branchUserName = branchUserName;
        this.branchPassword = branchPassword;
        this.branchName = branchName;
        this.branchPhoneNumber = branchPhoneNumber;
        this.branchAddress = branchAddress;
    }

    public String getBranchUserName() {
        return branchUserName;
    }

    public void setBranchUserName(String branchUserName) {
        this.branchUserName = branchUserName;
    }

    public String getBranchPassword() {
        return branchPassword;
    }

    public void setBranchPassword(String branchPassword) {
        this.branchPassword = branchPassword;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchPhoneNumber() {
        return branchPhoneNumber;
    }

    public void setBranchPhoneNumber(String branchPhoneNumber) {
        this.branchPhoneNumber = branchPhoneNumber;
    }

    public String getBranchServices() {
        return branchServices;
    }

    public void setBranchServices(String branchServices) {
        this.branchServices = branchServices;
    }

    public String getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(String openingDays) {
        this.openingDays = openingDays;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
