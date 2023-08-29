package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.TransferDTO;
import com.giovanniOpenclassrooms.paymybuddy.business.PersonService;
import com.giovanniOpenclassrooms.paymybuddy.business.TransactionService;
import com.giovanniOpenclassrooms.paymybuddy.constants.PageView;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NegativeBalanceAccountException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * Handler method to handle the transfer page
     *
     * @param authentication information about the person connected to the application
     * @param model          attribute to be passed to the front
     */
    @GetMapping("/transfer")
    public String getTransfer(Authentication authentication, Model model, @RequestParam(defaultValue = "1") Integer page) {
        int size = PageView.TRANSACTION_BY_PAGE_PERSON_VIEW;


        Person connectedPerson = personService.getPersonByEmail(authentication.getName());

        model.addAttribute("transferDTO", new TransferDTO());

        Person person = personService.getPersonByEmail(authentication.getName());
        model.addAttribute("connections", person.getConnectionsList());
        model.addAttribute("personBalance", connectedPerson.getAmountBalance());

        model.addAttribute("authentication", authentication.getName());

        Page<Transaction> transactions = transactionService.getPagedTransactionsByPersonSortByMostRecentDate(connectedPerson, PageRequest.of(page - 1, size));
        model.addAttribute("transactions", transactions.getContent());
        model.addAttribute("currentPage", transactions.getNumber() + 1);
        model.addAttribute("totalItems", transactions.getTotalElements());
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("pageSize", size);

        return "transfer";

    }

    /**
     * Handler method to handle add connection
     *
     * @param authentication information about the person connected to the application
     * @param friendEmail    the email of the person to make the connection
     * @param model          attribute to be passed to the front
     * @return the transfer page
     */
    @PostMapping("/transfer/add-friend")
    public String addFriend(Authentication authentication, String friendEmail, Model model) {

        try {
            personService.addConnection(
                    personService.getPersonByEmail(authentication.getName()),
                    personService.getPersonByEmail(friendEmail));

            model.addAttribute("successAddConnection", true);

        } catch (Exception exception) {
            model.addAttribute("failedAddConnection", exception.getMessage());
            return "transfer";

        } finally {
            getTransfer(authentication, model, 1);
        }
        return "transfer";
    }


    @PostMapping("/transfer/credit-account")
    public String creditMoneyOnPMBAccount(Authentication authentication, BigDecimal creditAmount, Model model) {

        try {
            transactionService.transferMoneyFromExternAccountToPMBAccount(authentication.getName(), creditAmount);
            model.addAttribute("successTransfer", true);

        } /*catch (Exception exception) {
            model.addAttribute("transferFailed", exception.getMessage());
            return "transfer";

        }*/ finally {
            getTransfer(authentication, model, 1);
        }
        return "transfer";
    }


    @PostMapping("/transfer/debit-account")
    public String debitMoneyFromPMBAccount(Authentication authentication, BigDecimal debitAmount, Model model) {

        try {
            transactionService.transferMoneyFromPMBAccountToExternAccount(authentication.getName(), debitAmount);
            model.addAttribute("successTransfer", true);

        } catch (NegativeBalanceAccountException negativeBalanceAccountException) {
            model.addAttribute("NotEnoughMoney", true);
            return "transfer";

        } /*catch (Exception exception) {
            model.addAttribute("transferFailed", exception.getMessage());
            return "transfer";

        } */ finally {
            getTransfer(authentication, model, 1);
        }
        return "transfer";
    }


    /**
     * Handler method to handle a transfer request between two persons
     *
     * @param authentication information about the person connected to the application
     * @param transferDTO    DTO who will take information about the forwarded transfer
     * @return the transfer page
     */
    @PostMapping("/transfer-request")
    public String sendMoney(Authentication authentication, @ModelAttribute("transferDTO") TransferDTO transferDTO, Model model) {

        try {
            Person debtor = personService.getPersonByEmail(authentication.getName());
            Person creditor = personService.getPersonByEmail(transferDTO.getCreditorEmail());
            BigDecimal amount = transferDTO.getAmount();
            String description = transferDTO.getDescription();

            transactionService.transferElectronicMoney(new TransactionDTO(debtor.getId(), creditor.getId(), amount, description));

            model.addAttribute("successTransfer", true);

        } catch (NegativeBalanceAccountException negativeBalanceAccountException) {
            model.addAttribute("NotEnoughMoney", true);
            return "transfer";
        } catch (Exception exception) {
            model.addAttribute("transferFailed", true);
            return "transfer";
        } finally {
            getTransfer(authentication, model, 1);
        }
        return "transfer";
    }

}
