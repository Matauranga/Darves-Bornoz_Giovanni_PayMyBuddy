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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {//TODO frank
    @Autowired
    private MockMvc mockMvc;


    @DisplayName("Try to perform method get on /login")
    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());

    }

    /*
    @DisplayName("Try to perform method get on /")
        @Test
        void login2() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isFound());

        }
    */

    @DisplayName("Try to perform method get on /signUp")
    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/signUp"))
                .andExpect(status().isOk());
    }

    @DisplayName("Try to save a person")
    @Test
    public void registration() throws Exception {
        //Given a DTO of a person who want to register
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("john", "doe", "2000-01-01", "johndoe@gmail.com", "123456");

        //When we initiate the request
        mockMvc.perform(post("/signUp/save")
                        .flashAttr("person", registerPersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/signUp?success"));
    }

    @DisplayName("Try to save a person without filling out the form")
    @Test
    public void registrationThrowInvalidField() throws Exception {
        //Given a DTO of a person who want to register
        RegisterPersonDTO registerPersonDTO = new RegisterPersonDTO("", "", "", "", "");

        //When we initiate the request
        mockMvc.perform(post("/signUp/save")
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
        mockMvc.perform(post("/signUp/save")
                        .flashAttr("person", registerPersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CONFLICT - email exists")));
    }

}