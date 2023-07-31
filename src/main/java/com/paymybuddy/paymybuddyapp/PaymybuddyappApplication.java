package com.paymybuddy.paymybuddyapp;

import com.paymybuddy.paymybuddyapp.DTO.TransactionDTO;
import com.paymybuddy.paymybuddyapp.business.TransactionService;
import com.paymybuddy.paymybuddyapp.business.PersonService;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.model.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.paymybuddy.paymybuddyapp.DTO.PersonDTO;
import com.paymybuddy.paymybuddyapp.DTO.TransactionDTO;
import com.paymybuddy.paymybuddyapp.business.PersonService;
import com.paymybuddy.paymybuddyapp.business.TransactionService;
import com.paymybuddy.paymybuddyapp.model.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;


@SpringBootApplication
public class PaymybuddyappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PaymybuddyappApplication.class, args);
    }

    @Autowired
    PersonService personService;
    @Autowired
    TransactionService transactionService;

    @Transactional
    public void run(String... args) throws Exception {
        PersonDTO personGio = new PersonDTO(1, "Gio", "darves", "gio.d@mail.fr", "Matau");
        PersonDTO personLu = new PersonDTO(2, "Lu", "Lu", "gio.d@mail.fr", "Matau");

        Person person = new Person("baba", "auRhum", "gio@mail.fr", "matau", new BigDecimal(125));


        personService.saveUser(personGio);
        personService.saveUser(personLu);

        personService.savePerson(person);
//        personService.addConnection(personGio, personLu);
//        TransactionDTO transactionDTO = new TransactionDTO(personGio.getPersonId(), personLu.getPersonId(), new BigDecimal("65.256"), "Yolo");
//
//        transactionService.transferElectronicMoney(transactionDTO);

    }
}
