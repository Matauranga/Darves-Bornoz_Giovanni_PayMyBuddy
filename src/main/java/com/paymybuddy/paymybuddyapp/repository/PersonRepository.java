package com.paymybuddy.paymybuddyapp.repository;

import com.paymybuddy.paymybuddyapp.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

    Person findByEmail(String email);
}
