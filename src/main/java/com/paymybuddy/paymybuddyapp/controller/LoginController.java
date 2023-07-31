package com.paymybuddy.paymybuddyapp.controller;

import com.paymybuddy.paymybuddyapp.DTO.PersonDTO;
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
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        PersonDTO user = new PersonDTO();
        model.addAttribute("user", user);
        return "register";
    }


    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") PersonDTO userDto,
                               BindingResult result,
                               Model model) {
        Person existingUser = personService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        personService.saveUser(userDto);
        return "redirect:/register?success";
    }


    @GetMapping("/users")
    public String users(Model model) {
        List<PersonDTO> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


}