package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.DTO.RegisterPersonDTO;
import com.paymybuddy.paymybuddyapp.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    Iterable<Person> getAllPersons();

    Optional<Person> getPersonById(UUID id);

    Person getPersonByEmail(String email);

    Person savePerson(Person personToCreate);

    void deletePerson(Person personToDelete);

    void addConnection(Person person, Person friend);

    void removeConnection(Person person, Person friendToDelete);


    void saveNewPersonFromDTO(RegisterPersonDTO registerPersonDto);


}
