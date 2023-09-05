package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.business.TransactionService;
import com.giovanniOpenclassrooms.paymybuddy.constants.PageView;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaxViewController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Handler method to handle home request
     *
     */
    @GetMapping("/tax-view")
    public void profile(Authentication authentication, Model model, @RequestParam(defaultValue = "1") Integer page) {
        int size = PageView.TRANSACTION_BY_PAGE_ADMIN_VIEW;

        model.addAttribute("authentication", authentication.getName());

        Page<Transaction> transactions = transactionService.getAllTransactionsPageable( PageRequest.of(page - 1, size));
        model.addAttribute("transactions", transactions.getContent());
        model.addAttribute("currentPage", transactions.getNumber() + 1);
        model.addAttribute("totalItems", transactions.getTotalElements());
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("pageSize", size);


    }

}
