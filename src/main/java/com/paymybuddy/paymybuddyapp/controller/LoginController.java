package com.paymybuddy.paymybuddyapp.controller;

import com.paymybuddy.paymybuddyapp.DTO.RegisterPersonDTO;
import com.paymybuddy.paymybuddyapp.DTO.TransactionDTO;
import com.paymybuddy.paymybuddyapp.DTO.TransferDTO;
import com.paymybuddy.paymybuddyapp.business.PersonService;
import com.paymybuddy.paymybuddyapp.business.TransactionService;
import com.paymybuddy.paymybuddyapp.model.Person;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/transfer")
    public String transfer(Authentication authentication, Model model) {
        model.addAttribute("transferDTO", new TransferDTO());

        Person person = personService.getPersonByEmail(authentication.getName());
        model.addAttribute("connections", person.getConnectionsList());
        model.addAttribute("transactionsList", transactionService.getTransactionsByUser(person));
        return "transfer";
    }


    // handler method to handle login request
    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }


    // handler method to handle user registration form request
    @GetMapping("/signUp")
    public String showRegistrationForm(Model model) {
        // create model object to store form data

        model.addAttribute("person", new RegisterPersonDTO());
        return "signUp";
    }


    // handler method to handle user registration form submit request
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


    @PostMapping("/transfer/addFriend")
    public String addFriend(Authentication authentication, String friendEmail) {

        personService.addConnection(
                personService.getPersonByEmail(authentication.getName()),
                personService.getPersonByEmail(friendEmail)
        );
        return "redirect:/transfer?successAddConnection";
    }


    @PostMapping("/transfer/transfer-request")
    public String sendMoney(Authentication authentication, @ModelAttribute("transferDTO") TransferDTO transferDTO) {

        Person debitor = personService.getPersonByEmail(authentication.getName());
        Person creditor = personService.getPersonByEmail(transferDTO.getCreditorEmail());
        BigDecimal amount = transferDTO.getAmount();
        String description = transferDTO.getDescription();

        transactionService.transferElectronicMoney(new TransactionDTO(debitor.getPersonId(), creditor.getPersonId(), amount, description));

        return "redirect:/transfer?successTransfer";

    }


}