package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import com.giovanniOpenclassrooms.paymybuddy.utils.PersonFaker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailsServiceTest {
    @Mock
    private PersonRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername() {
        //Given
        final Person person = PersonFaker.generate();
        final String personEmail = person.getEmail();

        //When
        when(userRepository.findByEmail(personEmail)).thenReturn(person);
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(personEmail);

        //Then
        assertNotNull(userDetails);
        assertEquals(person.getEmail(), ReflectionTestUtils.getField(userDetails, "username"));
    }

    @Test
    void loadUserByUsernameFailed() {
        //Given
        final Person person = PersonFaker.generate();
        final String personEmail = person.getEmail();

        //When
        when(userRepository.findByEmail(any())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(personEmail));

        //Then

    }

    @Test
    void getAuthorities() { //TODO Frank
    }
}