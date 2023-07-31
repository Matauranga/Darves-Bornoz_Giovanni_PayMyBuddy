package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.DTO.TransactionDTO;
import com.paymybuddy.paymybuddyapp.model.Person;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.repository.PersonRepository;
import com.paymybuddy.paymybuddyapp.repository.TransactionRepository;
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
                .filter(transaction -> person.getPersonId().equals(transaction.getDebtors().getPersonId())
                        || person.getPersonId().equals(transaction.getCreditors().getPersonId()))
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

        var transaction = new Transaction(debtor.getPersonId(), creditor.getPersonId(), amount, transactionDTO.message());

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
