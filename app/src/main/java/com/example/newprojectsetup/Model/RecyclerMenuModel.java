package com.example.newprojectsetup.Model;

public class RecyclerMenuModel {
    int icon;
    String title;


    public RecyclerMenuModel(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public RecyclerMenuModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
