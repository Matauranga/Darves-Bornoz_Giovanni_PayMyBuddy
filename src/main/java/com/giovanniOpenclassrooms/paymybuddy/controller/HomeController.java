package com.giovanniOpenclassrooms.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Handler method to handle home request
     *
     * @return the home page
     */
    @GetMapping("/home")
    public String profile() {

        return "home";
    }

}
