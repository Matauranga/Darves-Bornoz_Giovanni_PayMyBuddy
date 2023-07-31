package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.DTO.TransactionDTO;
import com.paymybuddy.paymybuddyapp.model.Person;
import com.paymybuddy.paymybuddyapp.model.Transaction;

import java.util.Optional;

public interface TransactionService {

    public Iterable<Transaction> getAllTransactions();

    public Optional<Transaction> getTransactionById(Integer idTransaction);

    public Iterable<Transaction> getTransactionsByUser(Person person);

    public void transferElectronicMoney(TransactionDTO transactionDTO);


}
