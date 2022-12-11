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
@RequestMapping(path="/UserController")
public class UserController {
    private String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private String username = "root";
    private String password = "Admin1234";
    private Connection connection;
    @Autowired
    public UserController(){
        try
        {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

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


}
