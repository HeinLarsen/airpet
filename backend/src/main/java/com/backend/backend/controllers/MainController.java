package com.backend.backend.controllers;

import com.backend.backend.models.User;
import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired

    private UserRepository userRepository;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser (@RequestParam String firstname, @RequestParam String lastName, @RequestParam String email) {
        User n = new User();
        n.setFirstname(firstname);
        n.setLastName(lastName);
        n.setEmail(email);

        userRepository.save(n);
        return "saved";
    }
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
}
