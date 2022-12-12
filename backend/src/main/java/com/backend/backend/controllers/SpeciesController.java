package com.backend.backend.controllers;

        import com.backend.backend.models.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/species")
public class SpeciesController {
    private final String url = "jdbc:mysql://localhost/airpets?" + "autoReconnect=true&useSSL=false";
    private final String username = "root";
    private final String password = "password";
    private Connection connection;

    @Autowired
    public SpeciesController() {
        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/getSpecies")
    public ResponseEntity<List<Species>> getBreeds() {
        String query = "select * from species";
        List<Species> species = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int ID = resultSet.getInt("ID");
                String specie = resultSet.getString("species");
                Species b = new Species(ID, specie);
                species.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(species, HttpStatus.OK);
    }

    @PostMapping(path = "/addBreed")
    public @ResponseBody String addNewBreed(@RequestBody Breeds b) {

        String query = "INSERT INTO breeds(breed) VALUES ('" + b.getBreed() + "')";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Adding breed...";
    }

}



