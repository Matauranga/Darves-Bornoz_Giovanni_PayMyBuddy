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
        Créer page Home "page d'aide"
        gérer deco auto avec securityconfig
        gérer les taxes sur transaction
        gérer relation avec compte externe
        gérer transaction dans profile
        revoir le digramme
        faire les script sql
        faire les livrables
        changer ordre des transactions dans la liste affichée
        + pagination si possible
 */