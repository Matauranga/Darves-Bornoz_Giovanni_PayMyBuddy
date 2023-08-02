package com.paymybuddy.paymybuddyapp.repository;

import com.paymybuddy.paymybuddyapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Person findByEmail(String email);

    boolean existsByEmail(String email);
}
