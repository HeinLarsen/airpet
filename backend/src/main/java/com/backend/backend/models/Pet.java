package com.backend.backend.models;

public class Pet {

    private String name;

    //it is a String in Breeds Table
    private int breed;

    // it's a String in Users Table
    private int owner;

    private int age;

    private String description;

    public Pet(String name, int breed, int owner, int age, String description) {
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.age = age;
        this.description = description;
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


}

