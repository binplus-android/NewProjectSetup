package com.example.newprojectsetup.Model;

public class CreateLeadEnquiryTypeModel {
    String enquiry_type;

    public CreateLeadEnquiryTypeModel(String enquiry_type) {
        this.enquiry_type = enquiry_type;
    }

    public CreateLeadEnquiryTypeModel() {
    }

    public String getEnquiry_type() {
        return enquiry_type;
    }

    public void setEnquiry_type(String enquiry_type) {
        this.enquiry_type = enquiry_type;
    }
}
