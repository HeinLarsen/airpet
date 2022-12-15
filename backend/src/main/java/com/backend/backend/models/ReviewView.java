package com.backend.backend.models;

public class ReviewView extends Review {
    private int ID;
    private String fullName;

    public ReviewView(int reviewer, int pet, String description, float rating, String date, int ID, String fullName) {
        super(reviewer, pet, description, rating, date);
        this.ID = ID;
        this.fullName = fullName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
