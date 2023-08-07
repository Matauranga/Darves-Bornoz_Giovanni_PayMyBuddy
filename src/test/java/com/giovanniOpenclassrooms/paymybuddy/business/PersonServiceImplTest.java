package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NotFoundException;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.PersonAlreadyExistsException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl personServiceImpl;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    PersonRepository personRepository;


    @DisplayName("Try to get all persons")
    @Test
    void getAllUsers() {
        //Given an initial list of persons
        Person person1 = new Person();
        Person person2 = new Person();
        List<Person> persons = List.of(person1, person2);

        //When we try to get all persons
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> response = personServiceImpl.getAllPersons();

        //Then we verify if we get persons
        assertThat(response).isNotEmpty().containsAll(persons);
        verify(personRepository, times(1)).findAll();
    }


    @DisplayName("Try to get a person by their ID")
    @Test
    void getPersonById() {
        //Given an initial person with an id
        UUID uuidPerson = UUID.fromString("c076de71-b095-4e98-a58d-3e0ec3199daf");
        Person person = new Person(uuidPerson, "Bgio", "Adar", LocalDate.of(2012, 10, 2), "g2@mail.fr", "$2a$10$ZSC9aOm3As6cyO4EvhBLwO4CkUv6QSOONxF4hJ0oYuSkbBV8ldoW.", new BigDecimal("100.00"), newArrayList());

        //When we try to get this person
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        Optional<Person> response = personServiceImpl.getPersonById(uuidPerson);

        //Then we verify if we have the good person and if all works correctly
        assertThat(response).isNotEmpty().contains(person);
        verify(personRepository, times(1)).findById(any());
    }


    @DisplayName("Try to get a person by their email")
    @Test
    void getPersonByEmail() {
        //Given an initial person to find
        String emailPersonToFind = "g@mail.fr";
        Person person = new Person("Gio", "Dar", emailPersonToFind, "$2a$10$ZSC9aOm3As6cyO4EvhBLwO4CkUv6QSOONxF4hJ0oYuSkbBV8ldoW.", new BigDecimal("100.00"));

        //When we search the person by his id
        when(personRepository.findByEmail(any())).thenReturn(person);
        Person response = personServiceImpl.getPersonByEmail(emailPersonToFind);

        //Then we verify if we get the good person
        assertThat(response.getEmail()).isEqualTo(emailPersonToFind);
        verify(personRepository, times(1)).findByEmail(any());
    }


    @DisplayName("Test to save a person")
    @Test
    void savePerson() {
        //Given a person to save
        Person personToAdd = new Person("Baba", "AuRhum", "dessert@email.fr", "baba", new BigDecimal("1.0"));

        //When we save the person
        personServiceImpl.savePerson(personToAdd);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).save(any());
    }


    @DisplayName("Test to update a person")
    @Test
    void updatePerson() {//TODO a modifier quant la fonction update sera modifié
        //Given an initial person to modify
        UUID uuidPerson = UUID.fromString("c076de71-b095-4e98-a58d-3e0ec3199daf");
        String newEmail = "noiretrouge@email.com";

        Person person = new Person(uuidPerson, "gio", "Adar", null, "g2@mail.fr", null, null, null);

        UpdatePersonDTO personTomodify = new UpdatePersonDTO("gio", "Adar", newEmail);

        //When we try to update the person
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        personServiceImpl.updatePerson(uuidPerson, personTomodify);

        //Then we verify if this have works correctly
        assertThat(person.getEmail()).isEqualTo(newEmail);
        verify(personRepository, times(1)).findById(any());
        verify(personRepository, times(1)).save(any());
    }


    @DisplayName("Test to update a person not existing")
    @Test
    void updatePersonNotExisting() {
        //Given an initial person to modify
        UUID uuidPerson = UUID.fromString("c076de71-b095-4e98-a58d-3e0ec3199daf");
        String newEmail = "noiretrouge@email.com";

        Person person = new Person(uuidPerson, "gio", "Adar", null, "g2@mail.fr", null, null, null);

        UpdatePersonDTO personTomodify = new UpdatePersonDTO("gio", "Mathy", newEmail);

        //When we try to update the person
        assertThrows(NotFoundException.class, () -> {
            when(personRepository.findById(any())).thenReturn(null);
            personServiceImpl.updatePerson(uuidPerson, personTomodify);
        });

        //Then we verify if we search the person, but we don't save the changement
        assertThat(person.getEmail()).isNotEqualTo(newEmail);
        verify(personRepository, times(1)).findById(any());
        verify(personRepository, times(0)).save(any());
    }


    @DisplayName("Test to delete a person")
    @Test
    void deletePerson() {
        //Given a person to delete
        Person personToDelete = new Person();

        //When we delete the person
        personServiceImpl.deletePerson(personToDelete);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).deleteById(any());
    }


    @DisplayName("Test to add a connection")
    @Test
    void addConnection() {
        //Given two persons to add connection between us
        Person person1 = new Person();
        Person person2 = new Person();

        //When we add the connection
        personServiceImpl.addConnection(person1, person2);

        //Then we verify if the connection exists and if there is saved
        assertThat(person1.getConnectionsList()).contains(person2);
        verify(personRepository, times(1)).save(any());
    }

    @DisplayName("Test to add a connection, but failed because of conception still exists")
    @Test
    void addConnectionFailed() {
        //Given two persons to add connection between us
        Person person2 = new Person();
        Person person1 = new Person(null, "Gio", "Dar", null, null, null, null, List.of(person2));

        //When we add the connection
        personServiceImpl.addConnection(person1, person2);

        //Then we verify if the connection still exists and if there is not saved
        assertThat(person1.getConnectionsList()).contains(person2);
        verify(personRepository, times(0)).save(any());
    }


    @DisplayName("test to remove a connection")
    @Test
    void removeConnection() {
        //Given two persons with a connection we want to remove
        Person person2 = new Person();
        Person person1 = new Person(null, "Gio", "Dar", null, null, null, null, new ArrayList<>(List.of(person2)));

        //When we try to remove connection
        personServiceImpl.removeConnection(person1, person2);

        //Then we verify if the connection is remove and if there is saved
        assertThat(person1.getConnectionsList()).isEmpty();
        verify(personRepository, times(1)).save(any());
    }

    @DisplayName("Save a new person from information in a RegisterPersonDTO")
    @Test
    void saveNewPersonFromDTO() throws PersonAlreadyExistsException {
        //Given initial RegisterPersonDTO
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("Baba", "AuRhum", "2023-07-31", "g@mail.fr", "Ahah");

        //When we try to save this person
        when(personRepository.existsByEmail(any())).thenReturn(false);
        personServiceImpl.saveNewPersonFromDTO(registerPersonDTO);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).save(any());

    }


    @DisplayName("Try to save a new person from information in a RegisterPersonDTO, but failed because of an existing email")
    @Test
    void saveNewPersonFromDTOFailed() {
        //Given initial RegisterPersonDTO
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("Baba", "AuRhum", "2023-07-31", "g@mail.fr", "Ahah");

        //When we try to save the person with an existing email
        assertThrows(PersonAlreadyExistsException.class, () -> {
            when(personRepository.existsByEmail(any())).thenReturn(true);
            personServiceImpl.saveNewPersonFromDTO(registerPersonDTO);
        });

        //Then we check that we haven't saved the person
        verify(personRepository, times(0)).save(any());
    }

}
