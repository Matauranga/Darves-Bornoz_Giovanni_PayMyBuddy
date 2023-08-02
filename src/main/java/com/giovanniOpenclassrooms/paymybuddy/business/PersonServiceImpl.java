package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }


    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }


    public Person savePerson(Person person) {
        return personRepository.save(person);
    }


    public void updatePerson(UUID id, UpdatePersonDTO person) {
        Person personToUpdate = this.personRepository.findById(id)
                .orElseThrow();

        personToUpdate.setFirstname(person.firstname());
        personToUpdate.setLastname(person.lastname());
        personToUpdate.setEmail(person.email());

        this.personRepository.save(personToUpdate);
    }

    public record UpdatePersonDTO(String firstname, String lastname, String email) { //TODO à voir
    }

    public void deletePerson(Person personToDelete) {
        personRepository.deleteById(personToDelete.getPersonId());
    }


    public void addConnection(Person person, Person friend) {

        person.getConnectionsList().add(friend);
        savePerson(person);
    }


    public void removeConnection(Person person, Person friendToDelete) {
        person.getConnectionsList().remove(friendToDelete);
        savePerson(person);
    }


    public void saveNewPersonFromDTO(RegisterPersonDTO registerPersonDTO) {
        // check if user exists, or throw exception
        if (personRepository.existsByEmail(registerPersonDTO.getEmail())) {
            throw new RuntimeException("CONFLICT - email exists");
        }
        Person person = new Person();
        person.setFirstname(registerPersonDTO.getFirstName());
        person.setLastname(registerPersonDTO.getLastName());
        person.setBirthdate(LocalDate.parse(registerPersonDTO.getBirthdate()));
        person.setEmail(registerPersonDTO.getEmail());

        // encrypt the password using spring security
        person.setPassword(passwordEncoder.encode(registerPersonDTO.getPassword()));

        savePerson(person);
    }


    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }


}
