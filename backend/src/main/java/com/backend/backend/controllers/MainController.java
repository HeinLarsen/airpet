package com.backend.backend.controllers;

import com.backend.backend.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/demo")
public class MainController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "password";
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



    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser (@RequestBody Users u) {
        String query = "INSERT INTO users (first_name, last_name, email) VALUES ('" + u.getFirstname() + "', '" + u.getLastName() + "', '" + u.getEmail() + "')";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "saved";
    }
}
