package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.UpdatePersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Try to perform method get on /profile")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void profile() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    @DisplayName("Test to modify the profile information")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void editProfileInformation() throws Exception {
        UpdatePersonDTO updatePersonDTO = new UpdatePersonDTO();
        ;
        updatePersonDTO.setFirstname("Tata");
        updatePersonDTO.setLastname("Yoyo");

        //When we initiate the request
        mockMvc.perform(post("/profile/edit")
                        .flashAttr("savePersonFromProfile", updatePersonDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tata")));
    }

}