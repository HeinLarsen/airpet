package com.backend.backend.models;

public class Pet {
    int ID;

    String name;

    //it is a String in Breeds Table
    int breed;

    // it's a String in Users Table
    int owner;

    int age;

    public Pet(String name, int breed, int owner, int age) {
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
}

