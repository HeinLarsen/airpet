package com.backend.backend.models;

public class Breeds {
    private int ID;

    private int species;

    private String breed;

    public Breeds(int ID, int species, String breed) {
        this.ID = ID;
        this.species = species;
        this.breed = breed;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
