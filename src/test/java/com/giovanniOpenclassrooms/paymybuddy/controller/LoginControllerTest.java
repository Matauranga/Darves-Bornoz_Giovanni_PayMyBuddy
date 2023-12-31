package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.RegisterPersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @DisplayName("Try to perform method get on /login")
    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());

    }

    @DisplayName("Try to perform method get on /error")
    @Test
    void error() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isFound());

    }

    @DisplayName("Try to perform method get on /signUp")
    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk());
    }

    @DisplayName("Try to save a person")
    @Test
    public void registration() throws Exception {
        //Given a DTO of a person who want to register
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("john", "doe", "2000-01-01", "johndoe@gmail.com", "123456");

        //When we initiate the request
        mockMvc.perform(post("/sign-up/save")
                        .flashAttr("person", registerPersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have successfully registered !")));
    }

    @DisplayName("Try to save a person without filling out the form")
    @Test
    public void registrationThrowInvalidField() throws Exception {
        //Given a DTO of a person who want to register
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("", "", "", "", "");

        //When we initiate the request
        mockMvc.perform(post("/sign-up/save")
                        .flashAttr("person", registerPersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk());
    }


    @DisplayName("Try to save a person who already exists")
    @Test
    public void registrationThrowPersonExistsException() throws Exception {
        //Given a DTO of a person who want to register
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("john", "doe", "2000-01-01", "g@mail.fr", "123456");

        //When we initiate the request
        mockMvc.perform(post("/sign-up/save")
                        .flashAttr("person", registerPersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Email already existing")));
    }

}