package com.paymybuddy.paymybuddyapp.repository;

import com.paymybuddy.paymybuddyapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
