package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Controller
public class LoginController {

    @Autowired
    private PersonService personService;


    /**
     * Handler method to handle login request
     *
     * @return the login page
     */
    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }


    /**
     * Handler method to handle person registration form request
     *
     * @param model
     * @return the sign-up page
     */
    @GetMapping("/signUp")
    public String showRegistrationForm(Model model) {

        // create model object to store form data
        model.addAttribute("person", new RegisterPersonDTO());
        return "signUp";
    }


    /**
     * Handler method to handle person registration form submit request
     *
     * @param registerPersonDTO the information from request to register a person
     * @param result
     * @param model
     * @return the sign-up page
     */
    @PostMapping("/signUp/save")
    public String registration(@Valid @ModelAttribute("person") RegisterPersonDTO registerPersonDTO, BindingResult result, Model model) {

        Person existingPerson = personService.getPersonByEmail(registerPersonDTO.getEmail());

        if (existingPerson != null && existingPerson.getEmail() != null && !existingPerson.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("person", registerPersonDTO);
            return "/signUp";
        }

        personService.saveNewPersonFromDTO(registerPersonDTO);
        return "redirect:/signUp?success";
    }

}
