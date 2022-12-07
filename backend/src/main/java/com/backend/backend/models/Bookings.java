package com.backend.backend.models;





public class Bookings {

    int ID;

    Pet pet;

    String bookee;

    int start;

    int end;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getBookee() {
        return bookee;
    }

    public void setBookee(String bookee) {
        this.bookee = bookee;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
