package com.paymybuddy.paymybuddyapp.controller;

import com.paymybuddy.paymybuddyapp.DTO.RegisterPersonDTO;
import com.paymybuddy.paymybuddyapp.business.PersonService;
import com.paymybuddy.paymybuddyapp.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private PersonService personService;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "loginExemple";
    }

    // handler method to handle user registration form request
    @GetMapping("/signUp")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO();
        model.addAttribute("person", registerPersonDTO);
        return "signUp";
    }


    // handler method to handle user registration form submit request
    @PostMapping("/signUp/save")
    public String registration(@Valid @ModelAttribute("person") RegisterPersonDTO registerPersonDTO,
                               BindingResult result,
                               Model model) {
        Person existingUser = personService.findUserByEmail(registerPersonDTO.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("person", registerPersonDTO);
            return "/signUp";
        }

        personService.saveUser(registerPersonDTO);
        return "redirect:/signUp?success";
    }


  /*  @GetMapping("/users")
    public String users(Model model) {
        List<RegisterPersonDTO> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }*/


}