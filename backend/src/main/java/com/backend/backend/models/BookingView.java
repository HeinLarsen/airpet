package com.backend.backend.models;

public class BookingView extends Bookings {
    private int ID;

    private String petName;

    private String petDescription;

    public BookingView(int pet, int bookee, String start, String end, int ID, String petName, String petDescription) {
        super(pet, bookee, start, end);
        this.ID = ID;
        this.petName = petName;
        this.petDescription = petDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }
}
