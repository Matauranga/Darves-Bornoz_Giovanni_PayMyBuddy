package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.DTO.PersonDTO;
import com.paymybuddy.paymybuddyapp.model.Person;
import com.paymybuddy.paymybuddyapp.model.Role;
import com.paymybuddy.paymybuddyapp.repository.PersonRepository;
import com.paymybuddy.paymybuddyapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;
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

    public void deletePerson(Person personToDelete) {
        personRepository.deleteById(personToDelete.getPersonId());
    }

    public void addConnection(Person person, Person friend) {
        person.getConnectionsList().add(friend);
    }

    public void removeConnection(Person person, Person friendToDelete) {
        person.getConnectionsList().remove(friendToDelete);
    }

    public record UpdatePersonDTO(String firstname, String lastname, String email) {
    }


    /**
     * @param personDTO
     */

    public void saveUser(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getFirstName() + " " + personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        // encrypt the password using spring security
        person.setPassword(passwordEncoder.encode(personDTO.getPassword()));

        personRepository.save(person);
    }


    public Person findUserByEmail(String email) {
        return personRepository.findByEmail(email);
    }


    public List<PersonDTO> findAllUsers() {
        List<Person> users = (List<Person>) personRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private PersonDTO mapToUserDto(Person person) {
        PersonDTO personDTO = new PersonDTO();
        String[] str = person.getName().split(" ");
        personDTO.setFirstName(str[0]);
        personDTO.setLastName(str[1]);
        personDTO.setEmail(person.getEmail());
        return personDTO;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }


/**
 *
 */
}
