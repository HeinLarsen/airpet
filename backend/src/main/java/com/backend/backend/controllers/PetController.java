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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/pet")
public class PetController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "password";
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


   /* public PetView getPet(ResultSet resultSet) throws SQLException
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
         p = new PetView(name, breed, owner, age, description, ID, rating, ratincCount, ownerName);
        return p;

    }*/




    @PostMapping(path ="/addPet")
    public @ResponseBody String addNewPet(@RequestBody Pet p){


        String query = "INSERT INTO pets (name, breed, species, owner, age, description, latitude, longitude) VALUES ('" + p.getName() + "', " + p.getBreed() + ", " + p.getSpecies() + ", " + p.getOwner() + ", " + p.getAge() + ", '" + p.getDescription() + "', " + p.getLatitude() + "," + p.getLongitude() + ")";
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
//VIRKER
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
                int species = resultSet.getInt("species");
                int owner = resultSet.getInt("owner");
                int age= resultSet.getInt("age");
                String description = resultSet.getString("description");
                float rating = resultSet.getFloat("rating");
                int ratingCount = resultSet.getInt("rating_count");
                String ownerName = resultSet.getString("owner_name");
                float latitude = resultSet.getFloat("latitude");
                float longitude = resultSet.getFloat("longitude");
                PetView p = new PetView(name, breed, species, owner, age, description, latitude, longitude, ID, rating, ratingCount, ownerName);


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

                    ID = resultSet.getInt("ID");
                    String name = resultSet.getString("name");
                    int breed = resultSet.getInt("breed");
                    int species = resultSet.getInt("species");
                    int owner = resultSet.getInt("owner");
                    int age= resultSet.getInt("age");
                    String description = resultSet.getString("description");
                    float rating = resultSet.getFloat("rating");
                    int ratingCount = resultSet.getInt("rating_count");
                    String ownerName = resultSet.getString("owner_name");
                    float latitude = resultSet.getFloat("latitude");
                    float longitude = resultSet.getFloat("longitude");
                    p = new PetView(name, breed, species, owner, age, description, latitude, longitude, ID, rating, ratingCount, ownerName);


                    return new ResponseEntity<>(p, HttpStatus.OK);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

public void testersql() throws SQLException{
        String query = "SELECT ID, start, end FROM bookings";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();

        while(resultSet.next()){
            int ID = resultSet.getInt("ID");
            String start = resultSet.getString("start");
            String end = resultSet.getString("end");

        }

}



}
