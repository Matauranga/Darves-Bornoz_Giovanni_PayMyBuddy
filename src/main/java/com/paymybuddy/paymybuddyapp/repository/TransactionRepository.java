package com.paymybuddy.paymybuddyapp.repository;

import com.paymybuddy.paymybuddyapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
