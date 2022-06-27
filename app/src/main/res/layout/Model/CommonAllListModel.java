package com.example.newprojectsetup.Model;

public class CommonAllListModel {

     String lead_num,lead_name;
    int leadimage;

    public CommonAllListModel(String lead_num, String lead_name, int leadimage) {
        this.lead_num = lead_num;
        this.lead_name = lead_name;
        this.leadimage = leadimage;
    }

    public CommonAllListModel() {
    }

    public String getLead_num() {
        return lead_num;
    }

    public void setLead_num(String lead_num) {
        this.lead_num = lead_num;
    }

    public String getLead_name() {
        return lead_name;
    }

    public void setLead_name(String lead_name) {
        this.lead_name = lead_name;
    }

    public int getLeadimage() {
        return leadimage;
    }

    public void setLeadimage(int leadimage) {
        this.leadimage = leadimage;
    }
}
