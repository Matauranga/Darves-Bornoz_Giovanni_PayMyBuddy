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
        gérer les taxes sur transaction inscrite en dure, gerer front avec code couleur pour transaction en plus et en moins
        gérer relation avec compte externe , pas de taxe
                mais par contre en faire des transaction classique avec message prédéfinis et montant taxe null
        Faire endpoint avec toute les transaction et les taxes affichés
        revoir le digramme
        faire les script sql
        faire les livrables

 */