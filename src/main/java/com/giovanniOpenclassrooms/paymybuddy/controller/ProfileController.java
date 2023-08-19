package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.PersonInformationDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @Autowired
    private PersonService personService;

    @Autowired
    private LoginController loginController;


    /**
     * Handler method to handle profile request
     *
     * @param authentication the parameter that contains information about the logged in person
     * @param model          attribute to be passed to the front
     * @return the profile page
     */
    @GetMapping("/profile")
    public String getProfile(Authentication authentication, Model model) {

        PersonInformationDTO personInformationDTO = personService.getPersonInformationDTOFromEmail(authentication.getName());

        model.addAttribute("UpdatePersonDTO", new UpdatePersonDTO());
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
    public String editProfileInformation(Authentication authentication, @Valid @ModelAttribute("savePersonFromProfile") UpdatePersonDTO updatePersonDTO, Model model) {

        try {

            personService.updatePerson(authentication.getName(), updatePersonDTO);

        } catch (Exception ignored) {

        } finally {
            getProfile(authentication, model);
        }

        return "profile";

    }

    /**
     * Handler method to delete a connection
     *
     * @param authentication      the parameter that contains information about the logged in person
     * @param emailFriendToDelete the email of the person to delete of the connection list
     * @param model               attribute to be passed to the front
     * @return the profile page
     */
    @PostMapping("/profile/editFriend")
    public String editFriendList(Authentication authentication, String emailFriendToDelete, Model model) {

        try {

            personService.removeConnection(personService.getPersonByEmail(authentication.getName()), personService.getPersonByEmail(emailFriendToDelete));

        } catch (Exception ignored) {

        } finally {
            getProfile(authentication, model);
        }

        return "profile";
    }


    /**
     * Handler method to delete an account
     *
     * @param authentication the parameter that contains information about the logged in person
     * @return the login page
     */
    @PostMapping("/delete")
    public String deleteAccount(Authentication authentication) { //TODO Frank pourquoi ça ne marche pas sur un des compte crée dans data.sql

        try {

            personService.deletePerson(personService.getPersonByEmail(authentication.getName()));

        } catch (Exception ignored) {

        } finally {
            loginController.login();
        }

        return "redirect:/login";
    }
}
