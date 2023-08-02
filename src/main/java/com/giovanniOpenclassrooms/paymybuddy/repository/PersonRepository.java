package com.giovanniOpenclassrooms.paymybuddy.repository;

import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    /**
     * Method to return a person by his email
     *
     * @param email of the person we want
     * @return the person if exists
     */
    Person findByEmail(String email);

    /**
     * Method to  verify if an email exists in the database
     *
     * @param email we want to check
     * @return true if the email still exists
     */
    boolean existsByEmail(String email);
}
