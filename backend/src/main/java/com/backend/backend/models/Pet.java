package com.backend.backend.models;

public class Pet {

    private String name;

    //it is a String in Breeds Table
    private int breed;

    private int species;

    // it's a String in Users Table
    private int owner;

    private int age;

    private String description;

    private float latitude;

    private float longitude;

    public Pet(String name, int breed, int species, int owner, int age, String description, float latitude, float longitude) {
        this.name = name;
        this.breed = breed;
        this.species = species;
        this.owner = owner;
        this.age = age;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBreed() {
        return breed;
    }

    public void setBreed(int breed) {
        this.breed = breed;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String setDescription() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}

