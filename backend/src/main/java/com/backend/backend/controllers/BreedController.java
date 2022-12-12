package com.backend.backend.controllers;

import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
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



