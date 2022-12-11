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
    public String addNewBooking(@RequestBody Bookings bo){


        String query = "INSERT INTO bookings(pet, bookee, start, end) VALUES (" + bo.getPet() + ", '" + bo.getBookee() + "', '" + bo.getStart() + "', '" + bo.getEnd() + "')";
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Adding your booking now...";
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

    //TODO
    //lav ListBookings


    //Skal refactores med anden kode


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
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");

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



    @PostMapping(path = "/addReview")
    public String addReview(@RequestBody Review review){
        System.out.println(review.getDate());
        String query = "insert into reviews (reviewer, pet, description, rating, date) values (" + review.getReviewer() + ", " + review.getPet() + ", '" + review.getDescription() + "'," + review.getRating() + ", '" + review.getDate() + "')";

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "adding review";
    }

    @GetMapping(path = "/getReviews/{id}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable("id") int id) {
        String query = "select reviews.*, concat(users.first_name, ' ', users.last_name) as fullName " +
                "from reviews " +
                "inner join users on reviews.reviewer = users.ID " +
                "where reviews.pet =" + id;
        List<Review> reviews = new ArrayList<Review>();

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.isBeforeFirst() ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid ID");
            } else {
                while(resultSet.next()){
                    id = resultSet.getInt("ID");
                    int reviewer = resultSet.getInt("reviewer");
                    int pet = resultSet.getInt("pet");
                    String description = resultSet.getString("description");
                    float rating = resultSet.getFloat("rating");
                    String date = resultSet.getString("date");
                    String fullName = resultSet.getString("fullName");
                    Review r = new Review(reviewer, pet, description, rating, date);
                    r.setID(id);
                    r.setFullName(fullName);
                    reviews.add(r);
                }
                return new ResponseEntity<>(reviews, HttpStatus.OK);

            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}

