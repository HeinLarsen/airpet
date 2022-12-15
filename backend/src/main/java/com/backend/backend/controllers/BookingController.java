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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/booking")
public class BookingController {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    @Autowired
    public BookingController(){
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
// tilføj funktionalitet så man ikke kan lave bookings der overlapper
    @PostMapping(path = "/addBooking")
    public String addNewBooking(@RequestBody Bookings bo) throws SQLException{
        String getDates = "SELECT * FROM bookings";
        System.out.println(getDates);
        Statement statement2 = this.connection.createStatement();
        statement2.execute(getDates);
        ResultSet resultSet = statement2.getResultSet();
        while(resultSet.next()){
            String startDate = resultSet.getString("start");
            String endDate = resultSet.getString("end");

            if(bo.getStart().equals(startDate) && bo.getEnd().equals(endDate) || bo.getStart().equals(startDate) || bo.getEnd().equals(endDate)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A booking already exists for this date range");
            }
        }

        String query = "INSERT INTO bookings(pet, bookee, start, end) VALUES (" + bo.getPet() + ", '" + bo.getBookee() + "', '" + bo.getStart() + "', '" + bo.getEnd() + "')";

        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Adding your booking now...";
    }

    @DeleteMapping(path = "/cancelBooking/{ID}")
    public @ResponseBody String cancelBooking(@PathVariable("ID") int ID)
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

    @GetMapping(path = "/listBookings/{ID}")
    public ResponseEntity<List<BookingView>> getBookings(@PathVariable("ID") int ID){
        String query = "SELECT * FROM bookings_view where bookee = "+ ID;
        ArrayList<BookingView> bookings = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                ID = resultSet.getInt("ID");
                int pet = resultSet.getInt("pet");
                int bookee = resultSet.getInt("bookee");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                String petName = resultSet.getString("pet_name");
                String petDescription = resultSet.getString("pet_description");
                BookingView b = new BookingView(pet, bookee, start, end, ID, petName, petDescription);
                bookings.add(b);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @GetMapping(path = "/listBooking/{ID}")
public ResponseEntity<ArrayList> checkBooking(@PathVariable("ID") int ID) throws SQLException{

    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        String query = "SELECT start, end FROM bookings WHERE pet = " + ID + " AND start >= '" + date + "' ";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        ArrayList<String> bookingDates = new ArrayList<>();
        while(resultSet.next())
        {

            String start = resultSet.getString("start");
            String end = resultSet.getString("end");
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            while(!startDate.isAfter(endDate))
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                System.out.println(startDate);
                bookingDates.add(startDate.format(formatter));
                startDate = startDate.plusDays(1);
            }
            }
        return new ResponseEntity<>(bookingDates, HttpStatus.OK);
}


}
