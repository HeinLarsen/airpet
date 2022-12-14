package com.backend.backend.controllers;
import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/pet")
public class PetController {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    @Autowired

    public PetController() {
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
        try
        {
            connection = DriverManager.getConnection(url, username, password);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public PetView getPet(ResultSet resultSet) throws SQLException
    {
        PetView p;
        int ID = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        int breed = resultSet.getInt("breed");
        int owner = resultSet.getInt("owner");
        int age = resultSet.getInt("age");
        String description = resultSet.getString("description");
        float rating = resultSet.getFloat("rating");
        int ratincCount = resultSet.getInt("rating_count");
        String ownerName = resultSet.getString("owner_name");
        float latitude = resultSet.getFloat("latitude");
        float longtitude = resultSet.getFloat("longitude");
        int species = resultSet.getInt("species");
         p = new PetView(name, breed, species, owner, age, description, latitude, longtitude, ID, rating, ratincCount, ownerName);
        return p;

    }




    @PostMapping(path ="/addPet")
    public @ResponseBody int addNewPet(@RequestBody Pet p){
        String query = "INSERT INTO pets (name, breed, species, owner, age, description, latitude, longitude) VALUES ('" + p.getName() + "', " + p.getBreed() + ", " + p.getSpecies() + ", " + p.getOwner() + ", " + p.getAge() + ", '" + p.getDescription() + "', " + p.getLatitude() + "," + p.getLongitude() + ")";
        int id;
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println(id);
                    return id;
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
//VIRKER
    @GetMapping(path = "/listPets")
    public ResponseEntity<List<PetView>> getPets(@RequestParam(required = false) int[] species, @RequestParam(required = false) int[] breeds ){
        String query = "SELECT * FROM pets_view";
        if (species != null || breeds != null) {
           query += " WHERE ";

        if (species.length > 0 && species != null) {
            query += "species in (";
            for (int i = 0; i < species.length; i++) {
                query += species[i];
                if (i != species.length - 1)
                {
                    query += ", ";
                }
            }
            query += ")";
        }

        if (breeds != null && breeds.length > 0) {
            if (species.length > 0 && species != null ) {
                query += " AND ";
            }
            query += "breed in (";
            for (int i = 0; i < breeds.length; i++) {
                query += breeds[i];
                if (i != breeds.length - 1) {
                    query += ", ";
                }
            }
            query += ")";
        }
        }
        ArrayList<PetView> pets = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                PetView p = getPet(resultSet);
                pets.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
//VIRKER
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
                    p = getPet(resultSet);
                    return new ResponseEntity<>(p, HttpStatus.OK);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



}
