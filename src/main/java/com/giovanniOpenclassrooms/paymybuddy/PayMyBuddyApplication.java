package com.giovanniOpenclassrooms.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }
}


/* TODO:
        gérer les taxes sur transaction
        gérer pagination /comprendre
        revoir le digramme
        faire les script sql
        faire les livrables
        ??? gérer relation avec compte externe ???
 */