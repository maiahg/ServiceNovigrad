package com.example.servicenovigrad;

public class Branch {
    String branchUserName, branchPassword, branchName, branchAddress, branchPhoneNumber, branchServices, workingDays, workingHours, branchRating, branchRatingCount;
    public Branch(){}

    public String getBranchRatingCount() {
        return branchRatingCount;
    }

    public void setBranchRatingCount(String branchRatingCount) {
        this.branchRatingCount = branchRatingCount;
    }

    public Branch(String branchUserName, String branchPassword, String branchName, String branchPhoneNumber, String branchAddress) {
        this.branchUserName = branchUserName;
        this.branchPassword = branchPassword;
        this.branchName = branchName;
        this.branchPhoneNumber = branchPhoneNumber;
        this.branchAddress = branchAddress;
    }

    public String getBranchRating() {
        return branchRating;
    }

    public void setBranchRating(String branchRating) {
        this.branchRating = branchRating;
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

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String openingHours) {
        this.workingHours = workingHours;
    }
}
