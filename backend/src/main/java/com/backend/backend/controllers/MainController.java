package com.backend.backend.controllers;

import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired

    private UserRepository userRepository;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser (@RequestParam String firstname, @RequestParam String email) {
        return "";
    }
}
