package com.backend.backend.controllers;
import com.backend.backend.models.*;
import org.apache.catalina.User;
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
import java.util.Map;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/user")
public class UserController {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    @Autowired
    public UserController() {
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

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //VIRKER
    @PostMapping(path = "/addUser")
    public ResponseEntity<String>  addNewUser(@RequestBody Users u) throws SQLException {
        String query = "SELECT email FROM users";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            if (u.getEmail().equals(email)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }
        }
        query = "INSERT INTO users(email, first_name, last_name, password, street, street_number, city, zip) VALUES ('" + u.getEmail() + "', '" + u.getFirstname() + "', '" + u.getLastName() + "', '" + u.getPassword() + "', '" + u.getStreet() + "', " + u.getStreetNumber() + ", '" + u.getCity() + "', " + u.getZip() + ")";
        statement = this.connection.createStatement();
        statement.execute(query);
        return new ResponseEntity<>("Your user has been created", HttpStatus.OK);
    }


    //lav en update user funktion og slet removeUser
    @PostMapping(path = "/removeUser")
    public @ResponseBody String removeUser(@RequestParam int ID) {


        String query = "DELETE FROM users WHERE ID =" + ID;
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Trying to remove User";
    }

    @PostMapping(path = "/updateUserName")
    public @ResponseBody String updateUserName(@RequestParam String username, int ID) {
        String query = "UPDATE users SET first_name =" + username + "WHERE ID=" + ID;

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "trying to update username";
    }


    public Users getUser(ResultSet resultSet) throws SQLException {
        Users u;
        int ID = resultSet.getInt("ID");
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
        return u;
    }

    @GetMapping(path = "/listUsers")
    public @ResponseBody Iterable<Users> getAllUsers() {
        String query = "SELECT * FROM users";
        ArrayList<Users> users = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Users u = getUser(resultSet);
                users.add(u);
            }
            for (Users u : users) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @GetMapping(path = "/getUser/{ID}")
    public ResponseEntity<Users> getUser(@PathVariable("ID") int ID) {
        String query = "SELECT * FROM users WHERE ID =" + ID;
        Users u;

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.isBeforeFirst()) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid ID");

            } else {
                while (resultSet.next()) {

                    u = getUser(resultSet);
                    u.setId(ID);
                    return new ResponseEntity<>(u, HttpStatus.OK);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<Users> login(@RequestBody Map<String, String> data) {
        String query = ("SELECT * FROM users WHERE email = '" + data.get("email") + "' AND password = '" + data.get("password") + "'");
        Users u;
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String street = resultSet.getString("street");
                int streetNumber = resultSet.getInt("street_number");
                String city = resultSet.getString("city");
                int zip = resultSet.getInt("zip");
                u = new Users(email, firstName, lastName, password, street, streetNumber, city, zip);
                u.setId(ID);
                return new ResponseEntity<>(u, HttpStatus.OK);
            }
        } catch (SQLException f) {
            f.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//VIRKER
    @PostMapping(path = "/resetPassword/{newPassword}/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("newPassword")String password, @PathVariable("email")String email) {

        Users u;
        try {
            String query= "SELECT * FROM users";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String mail = resultSet.getString("email");
                if (email.equals(mail)) {
                    query = "UPDATE users SET password = '" + password + "' WHERE email= '" + email + "'";
                    statement = this.connection.createStatement();
                    statement.executeUpdate(query);
                    return new ResponseEntity<>("password changed", HttpStatus.OK);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong, please try again", HttpStatus.BAD_REQUEST);
    }


}
