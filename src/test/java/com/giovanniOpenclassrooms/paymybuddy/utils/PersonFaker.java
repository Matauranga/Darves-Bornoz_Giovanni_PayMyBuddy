package com.giovanniOpenclassrooms.paymybuddy.utils;

import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.UUID;

public class PersonFaker {

    private static final Faker faker = new Faker(Locale.FRANCE);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static Person generate() {
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setFirstname(faker.address().firstName());
        person.setLastname(faker.address().lastName());
        person.setEmail(faker.internet().emailAddress());
        person.setPassword(passwordEncoder.encode(faker.lorem().word()));
        return person;
    }

}
