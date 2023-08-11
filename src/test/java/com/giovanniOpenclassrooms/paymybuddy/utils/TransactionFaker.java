package com.giovanniOpenclassrooms.paymybuddy.utils;

import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

public class TransactionFaker {

    private static final Faker faker = new Faker(Locale.FRANCE);


    public static Transaction generate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setCreditor(PersonFaker.generate());
        transaction.setDebtor(PersonFaker.generate());
        transaction.setTransferAmount(BigDecimal.valueOf(faker.number().randomNumber()));
        transaction.setDescription(faker.lorem().paragraph());
        return transaction;
    }

}
