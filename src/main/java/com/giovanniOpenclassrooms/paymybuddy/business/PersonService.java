package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.PersonInformationDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    List<Person> getAllPersons();

    Optional<Person> getPersonById(UUID id);

    Person getPersonByEmail(String email);

    Person savePerson(Person personToCreate);

    void updatePerson(String authenticationEmail, UpdatePersonDTO person);

    void deletePerson(Person personToDelete);

    void addConnection(Person person, Person friend);

    void removeConnection(Person person, Person friendToDelete);

    void saveNewPersonFromDTO(RegisterPersonDTO registerPersonDto);

    PersonInformationDTO getPersonInformationDTOFromEmail(String email);

}
