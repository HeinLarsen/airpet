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
@RequestMapping(path="/review")
public class ReviewController {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    @Autowired
    public ReviewController() {
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
//VIRKER
    public ReviewView getReview(ResultSet resultSet) throws SQLException
    {
        ReviewView r;
        int ID = resultSet.getInt("ID");
        int reviewer = resultSet.getInt("reviewer");
        int pet = resultSet.getInt("pet");
        String description = resultSet.getString("description");
        float rating = resultSet.getFloat("rating");
        String date = resultSet.getString("date");
        String fullName = resultSet.getString("full_name");
        r = new ReviewView(reviewer, pet, description, rating, date, ID, fullName);
        return r;
    }
//VIRKER
    @PostMapping(path = "/addReview")
    public ResponseEntity<String> addReview(@RequestBody Review review){
        String query = "insert into reviews (reviewer, pet, description, rating, date) values (" + review.getReviewer() + ", " + review.getPet() + ", '" + review.getDescription() + "'," + review.getRating() + ", '" + review.getDate() + "')";

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("adding review", HttpStatus.OK);
    }
//VIRKER
    @GetMapping(path = "/getReviews/{id}")
    public ResponseEntity<List<ReviewView>> getReviews(@PathVariable("id") int id) {
        String query = "select * from reviews_view where pet = "+id;
        List<ReviewView> reviews = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.isBeforeFirst() ) {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            } else {
                while(resultSet.next()){
                    ReviewView r = getReview(resultSet);
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
