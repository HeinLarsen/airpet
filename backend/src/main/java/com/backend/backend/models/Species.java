package com.backend.backend.models;

public class Species {
    private int ID;

    private String species;

    public Species(int ID, String species) {
        this.ID = ID;
        this.species = species;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
