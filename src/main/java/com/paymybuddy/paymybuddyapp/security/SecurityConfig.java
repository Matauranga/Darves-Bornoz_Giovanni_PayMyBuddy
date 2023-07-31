package com.paymybuddy.paymybuddyapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //TODO penser avant de rendre le projet à verifier si possible de changer les methods depreciate
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/signUp/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users").permitAll()
                                .requestMatchers(toH2Console())
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).formLogin(
                        form -> form //TODO : Franck l'ordre est'il important ici ou au dessus par ex ? et si oui comment on le pense ?
                                .loginPage("/login")
                                .usernameParameter("email")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).headers()
                .frameOptions()
                .disable();

        return http.build();
    }

    //TODO : Franck comment ça marche ?
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}