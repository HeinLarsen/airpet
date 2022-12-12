package com.backend.backend.controllers;

import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/breed")
public class BreedController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "password";
    private Connection connection;
    @Autowired
    public BreedController() {
        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/getBreeds")
    public ResponseEntity<List<Breeds>> getBreeds() {
        String query = "select * from breeds";
        List<Breeds> breeds = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int ID = resultSet.getInt("ID");
                int species = resultSet.getInt("species");
                String breed = resultSet.getString("breed");
                Breeds b = new Breeds(ID, species, breed);
                breeds.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(breeds, HttpStatus.OK);
    }

    @PostMapping(path = "/addBreed")
    public @ResponseBody String addNewBreed(@RequestBody Breeds b) {

        String query = "INSERT INTO breeds(breed) VALUES ('" + b.getBreed() + "')";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Adding breed...";
    }

}



