package com.paymybuddy.paymybuddyapp;

import com.paymybuddy.paymybuddyapp.business.TransactionService;
import com.paymybuddy.paymybuddyapp.business.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.paymybuddy.paymybuddyapp.DTO.RegisterPersonDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;


@SpringBootApplication
public class PaymybuddyappApplication/* implements CommandLineRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(PaymybuddyappApplication.class, args);
    }

   /* @Autowired
    PersonService personService;
    @Autowired
    TransactionService transactionService;

    @Transactional
    public void run(String... args) throws Exception {
        RegisterPersonDTO personGio = new RegisterPersonDTO("Gio", "darves", new Date(20 / 02 / 1999), "gio.d@mail.fr", "Matau");
        RegisterPersonDTO personLu = new RegisterPersonDTO("Lu", "Lu", new Date(20 / 05 / 1999), "g@mail.fr", "bah");


        personService.saveUser(personGio);
        personService.saveUser(personLu);

//        personService.addConnection(personGio, personLu);
//        TransactionDTO transactionDTO = new TransactionDTO(personGio.getPersonId(), personLu.getPersonId(), new BigDecimal("65.256"), "Yolo");
//
//        transactionService.transferElectronicMoney(transactionDTO);

    }*/
}
