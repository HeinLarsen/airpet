package com.backend.backend.controllers;
import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/PetController")
public class PetController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "password";
    private Connection connection;
    @Autowired

    public PetController() {
        try
        {
            connection = DriverManager.getConnection(url, username, password);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @PostMapping(path ="/addPet")
    public @ResponseBody String addNewPet(@RequestBody Pet p){


        String query = "INSERT INTO pets (name, breed, owner, age, description) VALUES ('" + p.getName() + "', " + p.getBreed() + ", " + p.getOwner() + ", " + p.getAge() + ", '" + p.getDescription() + "')";
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "trying to add pets... check run tab";
    }

    @PostMapping(path = "/removePet")
    public @ResponseBody String removePet(@RequestParam int ID)
    {
        String query = "DELETE FROM pets WHERE ID =" + ID;
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "trying to remove selected pet:" + ID;
    }

    @GetMapping(path = "/listPets")
    public ResponseEntity<List<PetView>> getPets(){
        String query = "SELECT * FROM pets_view";
        ArrayList<PetView> pets = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                int breed = resultSet.getInt("breed");
                int owner = resultSet.getInt("owner");
                int age= resultSet.getInt("age");
                String description = resultSet.getString("description");
                float rating = resultSet.getFloat("rating");
                int ratingCount = resultSet.getInt("rating_count");
                String ownerName = resultSet.getString("owner_name");
                PetView p = new PetView(name, breed, owner, age, description, ID, rating, ratingCount, ownerName);
                pets.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping(path = "/getPet/{ID}")
    public ResponseEntity<PetView> getPet(@PathVariable("ID") int ID){
        String query = "SELECT * FROM pets_view WHERE ID =" +ID;
        PetView p;

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.isBeforeFirst() ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid ID");
            } else {
                while(resultSet.next()){
                    ID = resultSet.getInt("ID");
                    String name = resultSet.getString("name");
                    int breed = resultSet.getInt("breed");
                    int owner = resultSet.getInt("owner");
                    int age= resultSet.getInt("age");
                    String description = resultSet.getString("description");
                    float rating = resultSet.getFloat("rating");
                    int ratingCount = resultSet.getInt("rating_count");
                    String ownerName = resultSet.getString("owner_name");
                    p = new PetView(name, breed, owner, age, description, ID, rating, ratingCount, ownerName);
                    return new ResponseEntity<>(p, HttpStatus.OK);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }





}
