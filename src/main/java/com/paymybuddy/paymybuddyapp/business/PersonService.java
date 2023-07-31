package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.DTO.RegisterPersonDTO;
import com.paymybuddy.paymybuddyapp.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    public Iterable<Person> getAllPersons();

    public Optional<Person> getPersonById(UUID id);

    public Person savePerson(Person personToCreate);

    public void deletePerson(Person personToDelete);

    public void addConnection(Person person, Person friend);

    public void removeConnection(Person person, Person friendToDelete);


    void saveUser(RegisterPersonDTO registerPersonDto);

    Person findUserByEmail(String email);

    //   List<RegisterPersonDTO> findAllUsers();

}
