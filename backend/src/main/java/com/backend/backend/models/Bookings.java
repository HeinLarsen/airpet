package com.backend.backend.models;





public class Bookings {

    int ID;

    int pet;

    int bookee;

    String start;

    String end;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public int getBookee() {
        return bookee;
    }

    public void setBookee(int bookee) {
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
