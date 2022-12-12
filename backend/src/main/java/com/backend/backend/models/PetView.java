package com.backend.backend.models;

public class PetView extends Pet {

    int ID;

    private float rating;

    private int ratingCount;

    private String ownerName;



    public PetView(String name, int breed, int owner, int age, String description, int ID, float rating, int ratingCount, String ownerName) {
        super(name, breed, owner, age, description);
        this.ID = ID;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.ownerName = ownerName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
