package com.backend.backend.controllers;

import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/breed")
public class BreedController {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    @Autowired
    public BreedController() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            url = prop.getProperty("spring.datasource.url");
            username = prop.getProperty("spring.datasource.username");
            password = prop.getProperty("spring.datasource.password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    public ResponseEntity<String>  addNewBreed(@RequestBody Breeds b) throws SQLException {
        String query = "SELECT breed FROM breeds";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        while(resultSet.next()){
            String breed = resultSet.getString("breed");
            if(b.getBreed().equals(breed)){
                return new  ResponseEntity<>("breed already exists", HttpStatus.IM_USED);
            }
        }

        query = "INSERT INTO breeds(species, breed) VALUES (" + b.getSpecies() +", '" + b.getBreed() + "')";
        try {
            Statement statement2 = this.connection.createStatement();
            statement2.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Your breed has been added", HttpStatus.OK);
    }

}



