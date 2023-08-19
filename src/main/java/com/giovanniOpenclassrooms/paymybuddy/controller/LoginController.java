package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
     * Handler method to handle error request
     *
     * @return the login page
     */
    @GetMapping("/error") //TODO marche pas
    public String error() {
        return "redirect:/login";
    }


    /**
     * Handler method to handle person registration form request
     *
     * @param model attribute to be passed to the front
     * @return the sign-up page
     */
    @GetMapping("/sign-up")
    public String showRegistrationForm(Model model) {

        // create model object to store form data
        model.addAttribute("person", new RegisterPersonDTO());
        return "sign-up";
    }


    /**
     * Handler method to handle person registration form submit request
     *
     * @param registerPersonDTO the information from request to register a person
     * @param model             attribute to be passed to the front
     * @return the sign-up page
     */
    @PostMapping("/sign-up/save")
    public String registration(@Valid @ModelAttribute("person") RegisterPersonDTO registerPersonDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("person", registerPersonDTO);
            return "sign-up";
        }

        try {
            personService.saveNewPersonFromDTO(registerPersonDTO);
            model.addAttribute("success", true);
        } catch (Exception exception) {

            model.addAttribute("errorMessage", exception.getMessage());
            return "sign-up";

        }

        return "sign-up";
    }

}
