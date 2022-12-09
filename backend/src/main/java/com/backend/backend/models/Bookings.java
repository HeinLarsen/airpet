package com.backend.backend.models;





public class Bookings {

    int ID;

    Pet pet;

    String bookee;

    String start;

    String end;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
