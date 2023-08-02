package com.giovanniOpenclassrooms.paymybuddy.business;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceImplTest {
   /* @InjectMocks
    PersonServiceImpl userServiceImpl;

    @Mock
    PersonRepository personRepository;

    private List<Person> initListUsers() {
   /*     Person person1 = new Person(1, "Gio", "gio", "rougeetnoir@email.com", "123", 250.0, newArrayList());
        Person person2 = new Person(2, "AGio", "gio", "noiretrouge@email.com", "456", 360.0, newArrayList());
        Person person3 = new Person(3, "BGio", "gio", "redandblack@email.com", "789", 120.0, List.of(person1, person2));

        List<Person> people = List.of(person1, person2, person3);

        when(personRepository.findAll()).thenReturn(people);
        return null;
    }

    @DisplayName("")
    @Test
    void getAllUsers() {
      /*  initListUsers();
        List<Person> response = (List<Person>) userServiceImpl.getAllUsers();

        assertThat(response).isNotEmpty();
        verify(personRepository, times(1)).findAll();

    }

    @DisplayName("Try to get an user by his ID")
    @Test
    void getUserById() {
     /*   List<Person> listPeople = initListUsers();

        when(personRepository.findById(2)).thenReturn(Optional.ofNullable(listPeople.get(2)));
        Optional<Person> response = userServiceImpl.getUserById(2);

        assertThat(response).isNotEmpty();
        verify(personRepository, times(1)).findById(any());
    }

    @DisplayName("Test to add an user")
    @Test
    void addUser() {
    /*    Person personToAdd = new Person(4, "Baba", "AuRhum", "dessert@email.fr", "baba", 1.0, newArrayList());

        userServiceImpl.saveUser(personToAdd);

        verify(personRepository, times(1)).save(any());

    }

    @DisplayName("Test to update an user")//TODO problem sur le test
    @Test
    void updateUser() {
    /*    Person personTomodify = new Person(2, "Baba", "AuRhum", "noiretrouge@email.com", "baba", 360.0, newArrayList());

        userServiceImpl.saveUser(personTomodify);

        verify(personRepository, times(1)).save(any());
    }

    @DisplayName("")
    @Test
    void deleteUser() {
        Person personToDelete = new Person();

        userServiceImpl.deleteUser(personToDelete);

        verify(personRepository, times(1)).deleteById(any());


    }

    @DisplayName("")
    @Test
    void addConnection() {
        List<Person> listPeople = initListUsers();

        userServiceImpl.addConnection(listPeople.get(0), listPeople.get(1));

        assertThat(listPeople.get(0).getConnectionsList()).contains(listPeople.get(1));
    }

    @Disabled
    @DisplayName("")
    @Test
    void removeConnection() { //TODO a corriger
/*
        Person person1 = new Person(1, "Gio", "gio", "rougeetnoir@email.com", "123", 250.0, newArrayList());
        Person person2 = new Person(2, "AGio", "gio", "noiretrouge@email.com", "456", 360.0, newArrayList());

        person1.setConnectionsList(List.of(person2));
        //person2.setConnectionsList(List.of(person1));


        userServiceImpl.removeConnection(person1, person2);

        assertThat(person1.getConnectionsList()).isEmpty();

    }*/
}
