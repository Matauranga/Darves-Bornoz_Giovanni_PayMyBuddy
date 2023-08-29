package com.giovanniOpenclassrooms.paymybuddy.utils;

import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class TransactionFaker {

    private static final Faker faker = new Faker(Locale.FRANCE);


    public static Transaction generate() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setCreditor(PersonFaker.generate());
        transaction.setDebtor(PersonFaker.generate());
        transaction.setTransferAmount(BigDecimal.valueOf(faker.number().randomNumber()));
        transaction.setOperationDate(LocalDateTime.now());
        transaction.setDescription(faker.lorem().paragraph());
        return transaction;
    }

}
