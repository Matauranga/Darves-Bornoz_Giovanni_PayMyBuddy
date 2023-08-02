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

    /**
     * Method that return all transactions in database
     *
     * @return a list of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Method that give a transaction by his id
     *
     * @param id of the desired transaction
     * @return the desired transaction with all infos in database
     */
    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    /**
     * Method that return all transactions for a user
     *
     * @param person the person whose transfer history we want
     * @return a List of all transfer where he person is involved (if change --> a List of all transfer where he person is involved like debtor)
     */
    @Override
    public List<Transaction> getTransactionsByUser(Person person) {//TODO : tout les transfers ou juste la premiere partie ?

        return getAllTransactions().stream()
                .filter(transaction -> person.getPersonId().equals(transaction.getDebtor().getPersonId())
                        || person.getPersonId().equals(transaction.getCreditor().getPersonId()))
                .toList();
    }


    /**
     * Method to proceed at a transfer money between two persons
     *
     * @param transactionDTO The DTO that contains all transfer information (debtorId, creditorId, amount, description)
     */
    public void transferElectronicMoney(TransactionDTO transactionDTO) {

        var amount = transactionDTO.amount();

        var debtor = getPersonOrElseThrow(transactionDTO.debtorId())
                .debitBalance(amount);

        var creditor = getPersonOrElseThrow(transactionDTO.creditorId())
                .creditBalance(amount);

        var transaction = new Transaction(debtor.getPersonId(), creditor.getPersonId(), amount, transactionDTO.description());

        this.personRepository.saveAll(List.of(creditor, debtor));
        this.transactionRepository.save(transaction);

    }

    /**
     * Methode that find a person by his ID or throw an exception
     *
     * @param id the ID of person search
     * @return the person
     */
    @NotNull
    private Person getPersonOrElseThrow(UUID id) {
        return this.personRepository
                .findById(id)
                .orElseThrow();
    }

}
