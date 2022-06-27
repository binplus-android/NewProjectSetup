package com.example.newprojectsetup.Model;

public class CreateLeadReqyurementModel {
    String requirement;

    public CreateLeadReqyurementModel(String requirement) {
        this.requirement = requirement;
    }

    public CreateLeadReqyurementModel() {
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
