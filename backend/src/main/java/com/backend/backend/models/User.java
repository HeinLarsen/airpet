package com.backend.backend.models;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "street")
    private String street;

    @Column(name = "streetNumber")
    private int streetNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;


    public User() {

    }


}
