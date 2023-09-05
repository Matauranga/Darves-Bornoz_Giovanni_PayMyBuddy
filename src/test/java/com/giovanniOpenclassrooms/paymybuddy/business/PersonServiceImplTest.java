package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.PersonInformationDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.AddConnectionFailedException;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NotFoundException;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.PersonAlreadyExistsException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import com.giovanniOpenclassrooms.paymybuddy.utils.PersonFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        List<Person> persons = List.of(PersonFaker.generate(), PersonFaker.generate());

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
        Person person = PersonFaker.generate();

        //When we try to get this person
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        Optional<Person> response = personServiceImpl.getPersonById(person.getId());

        //Then we verify if we have the good person and if all works correctly
        assertThat(response).isNotEmpty().contains(person);
        verify(personRepository, times(1)).findById(any());
    }


    @DisplayName("Try to get a person by their email")
    @Test
    void getPersonByEmail() {
        //Given an initial person to find
        String emailPersonToFind = "g@mail.fr";
        Person person = PersonFaker.generate();
        person.setEmail(emailPersonToFind);

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
        Person personToAdd = PersonFaker.generate();

        //When we save the person
        personServiceImpl.savePerson(personToAdd);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).save(any());
    }


    @DisplayName("Test to update a person")
    @Test
    void updatePerson() {
        //Given an initial person to modify and the updates
        Person person = PersonFaker.generate();
        String authenticationEmail = person.getEmail();

        String newFirstname = "Crash";
        String newLastname = "Bandicoot";
        UpdatePersonDTO personToModify = new UpdatePersonDTO();
        personToModify.setFirstname(newFirstname);
        personToModify.setLastname(newLastname);

        //When we try to update the person
        when(personRepository.existsByEmail(any())).thenReturn(true);
        when(personRepository.findByEmail(any())).thenReturn(person);
        personServiceImpl.updatePerson(authenticationEmail, personToModify);

        //Then we verify if this have works correctly
        assertThat(person.getFirstname()).isEqualTo(newFirstname);
        assertThat(person.getLastname()).isEqualTo(newLastname);
        verify(personRepository, times(1)).existsByEmail(any());
        verify(personRepository, times(1)).findByEmail(any());
        verify(personRepository, times(1)).save(any());
    }


    @DisplayName("Test to update a person not existing")
    @Test
    void updatePersonNotExisting() {
        //Given an initial person to modify and the updates
        Person person = PersonFaker.generate();
        String authenticationEmail = person.getEmail();

        String newFirstname = "Crash";
        String newLastname = "Bandicoot";
        UpdatePersonDTO personToModify = new UpdatePersonDTO();
        personToModify.setFirstname(newFirstname);
        personToModify.setLastname(newLastname);

        //When we try to update the person
        when(personRepository.existsByEmail(any())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> personServiceImpl.updatePerson(authenticationEmail, personToModify));

        //Then we verify if we search the person, but we don't save the changement
        assertThat(person.getFirstname()).isNotEqualTo(newFirstname);
        assertThat(person.getLastname()).isNotEqualTo(newLastname);
        verify(personRepository, times(1)).existsByEmail(any());
        verify(personRepository, times(0)).save(any());
    }

    @DisplayName("Test to update a person with blanks, proposition")
    @Test
    void updatePersonWithBlanksProposition() {
        //Given an initial person to modify and the updates
        Person person = PersonFaker.generate();
        String authenticationEmail = person.getEmail();

        String newFirstname = "";
        String newLastname = "";
        UpdatePersonDTO personToModify = new UpdatePersonDTO();
        personToModify.setFirstname(newFirstname);
        personToModify.setLastname(newLastname);

        //When we try to update the person
        when(personRepository.existsByEmail(any())).thenReturn(true);
        when(personRepository.findByEmail(any())).thenReturn(person);
        personServiceImpl.updatePerson(authenticationEmail, personToModify);

        //Then we verify if this have works correctly
        assertThat(person.getFirstname()).isNotEqualTo(newFirstname);
        assertThat(person.getLastname()).isNotEqualTo(newLastname);
        verify(personRepository, times(1)).existsByEmail(any());
        verify(personRepository, times(1)).findByEmail(any());
        verify(personRepository, times(1)).save(any());
    }


    @DisplayName("Test to add a connection")
    @Test
    void addConnection() {
        //Given two persons to add connection between us
        Person person1 = PersonFaker.generate();
        Person person2 = PersonFaker.generate();

        //When we add the connection
        when(personRepository.existsByEmail(any())).thenReturn(true);
        personServiceImpl.addConnection(person1, person2);

        //Then we verify if the connection exists and if there is saved
        assertThat(person1.getConnectionsList()).contains(person2);
        verify(personRepository, times(1)).save(any());
    }

    @DisplayName("Test to add a connection, but failed because of conception still exists")
    @Test
    void addConnectionFailed() {
        //Given two persons to add connection between us
        Person person1 = PersonFaker.generate();
        Person person2 = PersonFaker.generate();
        person1.setConnectionsList(List.of(person2));

        //When we add the connection
        when(personRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(AddConnectionFailedException.class, () -> personServiceImpl.addConnection(person1, person2));

        //Then we verify if the connection still exists and if there is not saved
        assertThat(person1.getConnectionsList()).contains(person2);
        verify(personRepository, times(0)).save(any());
    }

    @DisplayName("Test to add a connection, but failed because person try to add himself")
    @Test
    void addConnectionFailedBecauseOfSameEmail() {
        //Given two persons to add connection between us
        Person person1 = PersonFaker.generate();


        //When we add the connection
        when(personRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(AddConnectionFailedException.class, () -> personServiceImpl.addConnection(person1, person1));

        //Then we verify if the connection still exists and if there is not saved
        assertThat(person1.getConnectionsList()).isEmpty();
        verify(personRepository, times(0)).save(any());
    }

    @DisplayName("Test to add a connection, but failed because of friend's email not register")
    @Test
    void addConnectionFailedBecauseFriendEmailNotExists() {
        //Given two persons to add connection between us
        Person person1 = PersonFaker.generate();
        Person person2 = PersonFaker.generate();

        //When we add the connection
        when(personRepository.existsByEmail(any())).thenReturn(false);
        assertThrows(AddConnectionFailedException.class, () -> personServiceImpl.addConnection(person1, person2));

        //Then we verify if the connection still exists and if there is not saved
        assertThat(person1.getConnectionsList()).isEmpty();
        verify(personRepository, times(0)).save(any());
    }


    @DisplayName("test to remove a connection")
    @Test
    void removeConnection() {
        //Given two persons with a connection we want to remove
        Person person2 = PersonFaker.generate();
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
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("Baba", "AuRhum", "2023-07-31", "g@mail.fr", "Aha");

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
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("Baba", "AuRhum", "2023-07-31", "g@mail.fr", "Aha");

        //When we try to save the person with an existing email
        when(personRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(PersonAlreadyExistsException.class, () -> personServiceImpl.saveNewPersonFromDTO(registerPersonDTO));

        //Then we check that we haven't saved the person
        verify(personRepository, times(0)).save(any());
    }

    @DisplayName("Try to create a DTO to return person information")
    @Test
    void getPersonInformationDTOFromEmail() {
        //Given initial person
        Person person = PersonFaker.generate();
        String personEmail = person.getEmail();
        Person friend = PersonFaker.generate();
        person.setConnectionsList(List.of(friend));

        //When we try to create the DTO to return the good information
        when(personRepository.existsByEmail(personEmail)).thenReturn(true);
        when(personRepository.findByEmail(personEmail)).thenReturn(person);
        PersonInformationDTO personInformationDTO = personServiceImpl.getPersonInformationDTOFromEmail(personEmail);

        //Then we check that we haven't saved the person
        assertThat(personInformationDTO.getEmail()).isEqualTo(personEmail);
        assertThat(personInformationDTO.getBirthdate()).isEqualTo(person.getBirthdate());
        assertThat(personInformationDTO.getPassword()).isEqualTo(null);
        verify(personRepository, times(1)).existsByEmail(any());
        verify(personRepository, times(1)).findByEmail(any());
    }

    @DisplayName("Try to create a DTO to return person information, but failed because person doesn't exists")
    @Test
    void getPersonInformationDTOFromEmailFailed() {
        //Given

        //When we try to save the person with an existing email
        when(personRepository.existsByEmail(any())).thenReturn(false);
        when(personRepository.findByEmail(any())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> personServiceImpl.getPersonInformationDTOFromEmail(any()));

        //Then we check that we haven't saved the person
        verify(personRepository, times(1)).existsByEmail(any());
        verify(personRepository, times(0)).findByEmail(any());
    }
}
