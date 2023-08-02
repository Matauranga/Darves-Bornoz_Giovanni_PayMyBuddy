package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    public List<Transaction> getAllTransactions();

    public Optional<Transaction> getTransactionById(Integer idTransaction);

    public List<Transaction> getTransactionsByUser(Person person);

    public void transferElectronicMoney(TransactionDTO transactionDTO);


}
