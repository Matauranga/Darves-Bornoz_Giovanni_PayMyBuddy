package com.giovanniOpenclassrooms.paymybuddy.repository;

import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Handle method to get all transaction of a person, sort by most recent date, and pageable
     *
     * @param creditor the person as she is creditor
     * @param debtor  the person as she is debtor
     * @param pageable how we handle pagination
     * @return a list of transactions
     */
    Page<Transaction> findAllByCreditorOrDebtorOrderByOperationDateDesc(Person creditor, Person debtor, Pageable pageable);
}
