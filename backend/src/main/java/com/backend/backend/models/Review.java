package com.backend.backend.models;

import java.time.LocalDate;

public class Review {
    private int ID;

    private int reviewer;

    private String fullName;

    private int pet;

    private String description;

    private float rating;


    private String date;

    public Review(int reviewer, int pet, String description, float rating, String date) {
        this.reviewer = reviewer;
        this.pet = pet;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
