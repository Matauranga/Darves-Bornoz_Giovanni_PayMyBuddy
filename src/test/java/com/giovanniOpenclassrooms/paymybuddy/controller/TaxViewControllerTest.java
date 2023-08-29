package com.giovanniOpenclassrooms.paymybuddy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxViewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Try to perform method get on /tax-view")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void taxView() throws Exception {
        mockMvc.perform(get("/tax-view"))
                .andExpect(status().isOk());

    }
}
