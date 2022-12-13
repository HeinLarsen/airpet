package com.backend.backend.controllers;

        import com.backend.backend.models.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

        import java.io.*;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/species")
public class SpeciesController {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    @Autowired
    public SpeciesController(){
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



}



