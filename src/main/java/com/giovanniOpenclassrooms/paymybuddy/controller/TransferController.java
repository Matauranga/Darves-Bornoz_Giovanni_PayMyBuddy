package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.TransferDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import com.giovanniOpenclassrooms.paymybuddy.business.TransactionService;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NegativeBalanceAccount;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
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
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;


    /**
     * @param authentication information about the person connected to the application
     * @param model
     * @returnthe the transfer page
     */
    @GetMapping("/transfer")
    public String transfer(Authentication authentication, Model model/*,@PathVariable(value = "pageNo") int pageNo*/) {
        // int pages = 1;

        model.addAttribute("transferDTO", new TransferDTO());

        Person person = personService.getPersonByEmail(authentication.getName());
        model.addAttribute("connections", person.getConnectionsList());


     /*   Page<Transaction> page = transactionService.findPaginated(pageNo, 1);
        List<Transaction> listEmployees = page.getContent(); //TODO : a modifier car envoi TOUTES transactions en bdd

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("transactionsList", listEmployees);
*/

        model.addAttribute("transactionsList", transactionService.getTransactionsByPerson(person));

        return "transfer";
    }


    /**
     * Handler method to handle add connection
     *
     * @param authentication information about the person connected to the application
     * @param friendEmail    the email of the person to make the connection
     * @return the transfer page
     */
    @PostMapping("/transfer/add-friend")
    public String addFriend(Authentication authentication, TransferDTO transferDTO, BindingResult result, String friendEmail, Model model) {

        try {
            personService.addConnection(
                    personService.getPersonByEmail(authentication.getName()),
                    personService.getPersonByEmail(friendEmail));

            model.addAttribute("successAddConnection", true);

        } catch (Exception exception) {
            model.addAttribute("failedAddConnection", exception.getMessage());

            return "transfer";

        } finally {
            Person person = personService.getPersonByEmail(authentication.getName());
            model.addAttribute("connections", person.getConnectionsList());
            model.addAttribute("transactionsList", transactionService.getTransactionsByPerson(person));

        }
        return "transfer";
    }


    /**
     * Handler method to handle a transfer request between two persons
     *
     * @param authentication information about the person connected to the application
     * @param transferDTO
     * @return the transfer page
     */
    @PostMapping("/transfer/transfer-request")
    public String sendMoney(Authentication authentication, @ModelAttribute("transferDTO") TransferDTO transferDTO, Model model) {

        try {
            Person debtor = personService.getPersonByEmail(authentication.getName());
            Person creditor = personService.getPersonByEmail(transferDTO.getCreditorEmail());
            BigDecimal amount = transferDTO.getAmount();
            String description = transferDTO.getDescription();

            transactionService.transferElectronicMoney(new TransactionDTO(debtor.getPersonId(), creditor.getPersonId(), amount, description));
            model.addAttribute("successTransfer", true);


        } catch (NegativeBalanceAccount negativeBalanceAccount) {
            model.addAttribute("NotEnoughMoney", true);
            return "transfer";
        } catch (Exception exception) {
            model.addAttribute("transferFailed", true);
            return "transfer";
        } finally {
            Person person = personService.getPersonByEmail(authentication.getName());
            model.addAttribute("connections", person.getConnectionsList());

            model.addAttribute("transactionsList", transactionService.getTransactionsByPerson(person));

        }
        return "transfer";
    }

}
