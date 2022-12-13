package com.backend.backend.models;
public class Bookings {
        private int ID;



    private int pet;

    private int bookee;

    private String start;

    private String end;


        private boolean isBooked;

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }





    public Bookings(int pet, int bookee, String start, String end) {
        this.pet = pet;
        this.bookee = bookee;
        this.start = start;
        this.end = end;
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
