package com.backend.backend.controllers;

import com.backend.backend.models.Bookings;
import com.backend.backend.models.Breeds;
import com.backend.backend.models.Pet;
import com.backend.backend.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.events.Event;

import java.sql.*;
import java.util.ArrayList;

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



    //VIRKER
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

    //VIRKER
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

    //VIRKER
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
    //VIRKER IKKE -> DATO & TID
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

    //VIRKER
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

    //SKAL TESTES
    @PostMapping(path = "/cancelBooking")
    public @ResponseBody String cancelBooking(@RequestParam int ID)
    {
        String query = "DELETE FROM bookings WHERE ID = " + ID;
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Cancelling your booking: Booking Number: " + ID;
    }

    //VIRKER
    @GetMapping(path = "/listUsers")
            public @ResponseBody Iterable<Users> getAllUsers(){
        String query = "SELECT * FROM users";
        ArrayList<Users> users = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int ID = resultSet.getInt("ID");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String street = resultSet.getString("street");
                int streetNumber = resultSet.getInt("street_number");
                String city = resultSet.getString("city");
                int zip = resultSet.getInt("zip");
                Users u = new Users(email, firstName, lastName, password, street, streetNumber, city, zip);
                u.setId(ID);
                users.add(u);
            }
            for(Users u : users){
                System.out.println(u);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
    //TODO
    //lav ListBookings

    //VIRKER
    @GetMapping(path = "/listPets")
    public @ResponseBody Iterable<Pet> getPets(){
        String query = "SELECT * FROM pets";
        ArrayList<Pet> pets = new ArrayList<>();
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

                Pet p = new Pet(name, breed, owner, age );
                p.setID(ID);
                pets.add(p);
            }
            for(Pet p : pets){
                System.out.println(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pets;
    }







    //Skal refactores med anden kode
    @PostMapping(path = "/updateUser")
    public @ResponseBody String removeUser(@RequestParam int ID){

        String query = "DELETE FROM users WHERE ID =" + ID;
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Trying to remove User";
    }

    @PostMapping(path = "/listBookings")
    public @ResponseBody  Iterable<Bookings> getBookings(){
        String query = "SELECT * FROM bookings";
        ArrayList<Bookings> bookings = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int ID = resultSet.getInt("ID");
                int pet = resultSet.getInt("pet");
                int bookee = resultSet.getInt("bookee");
                Date start = resultSet.getDate("start");
                Date end = resultSet.getDate("end");

                Bookings bookings1 = new Bookings();
                bookings1.setID(ID);
                bookings.add(bookings1);
            }
            for(Bookings b : bookings){
                System.out.println(bookings);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bookings;
    }
    //TODO
    //lave metoder der kan hente specifikke pets, users og senere bookings
    @GetMapping(path = "/getUser/{ID}")
    public ResponseEntity<Users> getUser(@PathVariable("ID") int ID){
        String query = "SELECT * FROM users WHERE ID =" +ID;
        Users u;

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.isBeforeFirst() ) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid ID");

            } else {
                while(resultSet.next()){
                    ID = resultSet.getInt("ID");
                    String email = resultSet.getString("email");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String password = resultSet.getString("password");
                    String street = resultSet.getString("street");
                    int streetNumber = resultSet.getInt("street_number");
                    String city = resultSet.getString("city");
                    int zip = resultSet.getInt("zip");
                    u = new Users(email, firstName, lastName, password, street, streetNumber, city, zip);
                    u.setId(ID);
                    return new ResponseEntity<>(u, HttpStatus.OK);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(path = "/getPet/{ID}")
    public ResponseEntity<Pet> getPet(@PathVariable("ID") int ID){
        String query = "SELECT * FROM pets WHERE ID =" +ID;
        Pet p;

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
                    int age = resultSet.getInt("age");
                    p = new Pet(name, breed, owner, age);
                    p.setID(ID);
                    return new ResponseEntity<>(p, HttpStatus.OK);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}

