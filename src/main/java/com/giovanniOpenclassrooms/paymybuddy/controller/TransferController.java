package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.TransferDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import com.giovanniOpenclassrooms.paymybuddy.business.TransactionService;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Controller
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PersonService personService;


    /**
     * @param authentication information about the person connected to the application
     * @param model
     * @returnthe the transfer page
     */
    @GetMapping("/transfer")
    public String transfer(Authentication authentication, Model model) {
        model.addAttribute("transferDTO", new TransferDTO());

        Person person = personService.getPersonByEmail(authentication.getName());
        model.addAttribute("connections", person.getConnectionsList());
        model.addAttribute("transactionsList", transactionService.getTransactionsByUser(person));
        return "transfer";
    }


    /**
     * Handler method to handle add connection
     *
     * @param authentication information about the person connected to the application
     * @param friendEmail    the email of the person to make the connection
     * @return the transfer page
     */
    @PostMapping("/transfer/addFriend")
    public String addFriend(Authentication authentication, String friendEmail) {

        personService.addConnection(
                personService.getPersonByEmail(authentication.getName()),
                personService.getPersonByEmail(friendEmail)
        );
        return "redirect:/transfer?successAddConnection";
    }


    /**
     * Handler method to handle a transfer request between two persons
     *
     * @param authentication information about the person connected to the application
     * @param transferDTO
     * @return the transfer page
     */
    @PostMapping("/transfer/transfer-request")
    public String sendMoney(Authentication authentication, @ModelAttribute("transferDTO") TransferDTO transferDTO) {

        Person debtor = personService.getPersonByEmail(authentication.getName());
        Person creditor = personService.getPersonByEmail(transferDTO.getCreditorEmail());
        BigDecimal amount = transferDTO.getAmount();
        String description = transferDTO.getDescription();

        transactionService.transferElectronicMoney(new TransactionDTO(debtor.getPersonId(), creditor.getPersonId(), amount, description));

        return "redirect:/transfer?successTransfer";

    }


}
