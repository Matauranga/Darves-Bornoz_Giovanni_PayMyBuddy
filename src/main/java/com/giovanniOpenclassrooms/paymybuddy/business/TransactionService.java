package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Optional<Transaction> getTransactionById(UUID transactionId);

    List<Transaction> getTransactionsByPerson(Person person);

    void transferElectronicMoney(TransactionDTO transactionDTO);

    Page<Transaction> findPaginated(int pageNo, int pageSize);


}
