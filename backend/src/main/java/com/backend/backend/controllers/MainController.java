package com.backend.backend.controllers;

import com.backend.backend.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@Controller
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
    public @ResponseBody String addNewUser (@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        Users n = new Users();
        n.setFirstname(firstName);
        n.setLastName(lastName);
        n.setEmail(email);


        String query = "INSERT INTO users (first_name, last_name, email) VALUES ('" + firstName + "', '" + lastName + "', '" + email + "')";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "saved";
    }
}
