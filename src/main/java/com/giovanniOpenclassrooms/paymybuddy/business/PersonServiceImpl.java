package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NotFoundException;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.PersonAlreadyExistsException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method that give all persons in the database
     *
     * @return a list of all persons
     */
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Method that return a person by his id
     *
     * @param id of the person search
     * @return return this person if she exists
     */
    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    /**
     * Method that return a person by his email
     *
     * @param email of the person search
     * @return return this person if she exists
     */
    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    /**
     * Method that save a person in database
     *
     * @param person to save
     * @return
     */
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    /**
     * Method to update person in database
     *
     * @param id     the id of the person we wants modify
     * @param person information to modify return by the front in a DTO
     */
    public void updatePerson(UUID id, UpdatePersonDTO person) {//TODO : à peaufiner
        Person personToUpdate = this.personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Person.class, "Person don't exist")); //TODO : Pk pas couvert quand execute all tests

        personToUpdate.setFirstname(person.firstname());
        personToUpdate.setLastname(person.lastname());
        personToUpdate.setEmail(person.email());

        this.personRepository.save(personToUpdate);
    }

    /**
     * Method to delete a person from database
     *
     * @param personToDelete the person to delete
     */
    public void deletePerson(Person personToDelete) {
        personRepository.deleteById(personToDelete.getPersonId());
    }

    /**
     * Method to add connection between two user of the application
     *
     * @param person the person who add connection
     * @param friend the person to connect to
     */
    public void addConnection(Person person, Person friend) {// TODO : vérifier si la personne est déjà dans la liste d'amis et renvoyer info dans front
        if (!person.getConnectionsList().contains(friend)) {
            person.getConnectionsList().add(friend);
            savePerson(person);
        }
    }

    /**
     * Method to remove a connection
     *
     * @param person         the person who want to delete the connection
     * @param friendToDelete the person whose connection you want to delete
     */
    public void removeConnection(Person person, Person friendToDelete) {
        person.getConnectionsList().remove(friendToDelete);
        savePerson(person);
    }

    /**
     * Method to save a new person from the registration system
     *
     * @param registerPersonDTO information from the front to register the person
     */
    public void saveNewPersonFromDTO(RegisterPersonDTO registerPersonDTO) {

        // check if user exists, or throw exception
        if (personRepository.existsByEmail(registerPersonDTO.getEmail())) {
            throw new PersonAlreadyExistsException(Person.class, "CONFLICT - email exists");
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

}
