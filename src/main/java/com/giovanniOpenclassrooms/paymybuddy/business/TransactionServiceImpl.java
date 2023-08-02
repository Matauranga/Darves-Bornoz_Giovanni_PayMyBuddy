package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import com.giovanniOpenclassrooms.paymybuddy.repository.TransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Iterable<Transaction> getTransactionsByUser(Person person) {

        return ((List<Transaction>) getAllTransactions()).stream()
                .filter(transaction -> person.getPersonId().equals(transaction.getDebtor().getPersonId())
                        || person.getPersonId().equals(transaction.getCreditor().getPersonId()))
                .toList();
    }


    /**
     * @param transactionDTO
     */
    public void transferElectronicMoney(TransactionDTO transactionDTO) {

        var amount = transactionDTO.amount();

        var debtor = getUserOrElseThrow(transactionDTO.debtorId())
                .debitBalance(amount);

        var creditor = getUserOrElseThrow(transactionDTO.creditorId())
                .creditBalance(amount);

        var transaction = new Transaction(debtor.getPersonId(), creditor.getPersonId(), amount, transactionDTO.description());

        this.personRepository.saveAll(List.of(creditor, debtor));
        this.transactionRepository.save(transaction);

    }

    @NotNull
    private Person getUserOrElseThrow(UUID id) {
        return this.personRepository
                .findById(id)
                .orElseThrow();
    }

}
