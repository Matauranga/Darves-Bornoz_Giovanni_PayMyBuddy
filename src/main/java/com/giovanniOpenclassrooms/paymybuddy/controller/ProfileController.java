package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.PersonInformationDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @Autowired
    private PersonService personService;


    /**
     * Handler method to handle profile request
     *
     * @param authentication the parameter that contains information about the logged in person
     * @param model          attribute to be passed to the front
     * @return the profile page
     */
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {

        model.addAttribute("UpdatePersonDTO", new UpdatePersonDTO());

        PersonInformationDTO personInformationDTO = personService.getPersonInformationDTOFromEmail(authentication.getName());
        model.addAttribute("personInformationDTO", personInformationDTO);

        return "profile";
    }

    /**
     * Handler method to handle the profile edition
     *
     * @param authentication  the parameter that contains information about the logged in person
     * @param updatePersonDTO The DTO wo contain the information to update
     * @param model           attribute to be passed to the front
     * @return the profile page
     */
    @PostMapping("/profile/edit")
    public String editProfileInformation(Authentication authentication, @Valid @ModelAttribute("savePersonFromProfile") UpdatePersonDTO updatePersonDTO, BindingResult result, Model model) {

        try {

            personService.updatePerson(authentication.getName(), updatePersonDTO);

        } catch (Exception ignored) {

        } finally {
            PersonInformationDTO personInformationDTO = personService.getPersonInformationDTOFromEmail(authentication.getName());
            model.addAttribute("personInformationDTO", personInformationDTO);
            model.addAttribute("UpdatePersonDTO", new UpdatePersonDTO());
        }

        return "profile";

    }


}
