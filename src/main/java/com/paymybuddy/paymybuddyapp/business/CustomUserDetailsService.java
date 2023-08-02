package com.paymybuddy.paymybuddyapp.business;

import com.paymybuddy.paymybuddyapp.model.Person;
import com.paymybuddy.paymybuddyapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person = userRepository.findByEmail(email);

        if (person == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new User
                    (
                            person.getEmail(),
                            person.getPassword(),
                            getAuthorities()
                    );

    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

}