package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.constants.Tax;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NotFoundException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import com.giovanniOpenclassrooms.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PersonService personService; //TODO Frank impl ou interface

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
     * @param transactionId of the desired transaction
     * @return the desired transaction with all infos in database
     */
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    /**
     * Method that return all transactions for a user
     *
     * @param person the person whose transfer history we want
     * @return a List of all transfer where he person is involved (if change --> a List of all transfer where he person is involved like debtor)
     */
    @Override
    public List<Transaction> getTransactionsByPerson(Person person) {//TODO  Frank : tout les transfers ou juste la premiere partie ?

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
    @Transactional
    public void transferElectronicMoney(TransactionDTO transactionDTO) {

        final BigDecimal amount = transactionDTO.amount().setScale(2,RoundingMode.HALF_EVEN);

        BigDecimal amountTax = amount.multiply(Tax.TAXE_VALUE).setScale(2, RoundingMode.HALF_EVEN);
        log.info("Amount of Tax to be deducted : {} €", amountTax);

        final BigDecimal amountAfterTax = amount.add(amountTax);
        log.info("Amount after Tax applied : {} €", amountAfterTax);

        final Person debtor = getPersonOrElseThrow(transactionDTO.debtorId()).debitBalance(amountAfterTax);

        final Person creditor = getPersonOrElseThrow(transactionDTO.creditorId()).creditBalance(amount);

        final var transaction = new Transaction(debtor, creditor, amount, amountTax, transactionDTO.description());

        personRepository.saveAll(List.of(creditor, debtor));
        transactionRepository.save(transaction);
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
                .orElseThrow(() -> new NotFoundException("Person not found"));
    }


    /**
     * @param email       the email of the person who want to take off his money
     * @param debitAmount The amount to take off at the account
     */
    @Transactional
    public void transferMoneyFromPMBAccountToExternAccount(String email, BigDecimal debitAmount) {

        final Person person = personService.getPersonByEmail(email);

        person.debitBalance(debitAmount);
        personRepository.save(person);

    }

    /**
     * @param email        the email of the person who want to add some money on his account
     * @param creditAmount The amount to add at the account
     */
    @Transactional
    public void transferMoneyFromExternAccountToPMBAccount(String email, BigDecimal creditAmount) {

        final Person person = personService.getPersonByEmail(email);

        person.creditBalance(creditAmount);
        personRepository.save(person);
    }


    public Page<Transaction> getPagedTransactionsByPersonSortByMostRecentDate(Person person, Pageable pageable) {
        return transactionRepository.findAllByCreditorOrDebtorOrderByOperationDateDesc(person, person, pageable);
    }

}
