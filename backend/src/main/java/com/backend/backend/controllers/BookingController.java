package com.backend.backend.controllers;

import com.backend.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
import java.util.ArrayList;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/booking")
public class BookingController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "password";
    private Connection connection;
    @Autowired
public BookingController(){
        try
        {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

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



}
