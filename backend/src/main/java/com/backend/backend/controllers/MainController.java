package com.backend.backend.controllers;

import com.backend.backend.models.Bookings;
import com.backend.backend.models.Breeds;
import com.backend.backend.models.Pet;
import com.backend.backend.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
@CrossOrigin(origins = "http://locahost:8081")
@RestController
@RequestMapping(path="/demo")
public class MainController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "Admin1234";
    private Connection connection;
    @Autowired

    public MainController() {
        try
        {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    @PostMapping(path = "/addUser")
    public @ResponseBody String addNewUser (@RequestBody Users u) {



        String query = "INSERT INTO users (first_name, last_name, email, password, street, street_number, city, zip) VALUES ('" + u.getFirstname() + "', '" + u.getLastName() + "', '" + u.getEmail() + "', '" + u.getPassword() + "', '" + u.getStreet() + "', " + u.getStreetNumber() + ", '" + u.getCity() + "', " + u.getZip() + ")";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Bing bong bam done...";
    }

    @PostMapping(path ="/addPet")
    public @ResponseBody String addNewPet(@RequestBody Pet p){


        String query = "INSERT INTO pets (name, breed, owner, age) VALUES ('" + p.getName() + "', " + p.getBreed() + ", " + p.getOwner() + ", " + p.getAge() + ")";
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "trying to add pets... check run tab";
    }

    @PostMapping(path = "/addBreed")
    public @ResponseBody String addNewBreed(@RequestBody Breeds b){

        String query = "INSERT INTO breeds(breed) VALUES ('" + b.getBreed() + "')";
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Adding breed...";
    }
    @PostMapping(path = "/addBooking")
    public @ResponseBody String addNewBooking(@RequestBody Bookings bo){

        String query = "INSERT INTO bookings(pet, bookee, start, end) VALUES (" + bo.getPet() + ", '" + bo.getBookee() + "', " + bo.getStart() + ", " + bo.getEnd() + ")";
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Adding your booking now...";
    }


}
