package com.giovanniOpenclassrooms.paymybuddy.controller;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransferDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Try to perform method get on /transfer")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void transfer() throws Exception {
        mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk());
    }


    @DisplayName("Test to add a connection")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void addConnection() throws Exception {
        //Given an email for the connection to add
        String friendEmail = "lu@mail.fr";

        //When we initiate the request
        mockMvc.perform(post("/transfer/addFriend")
                        .param("friendEmail", friendEmail))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?successAddConnection"));
    }

    @DisplayName("Try to add a connection but failed")
    @Test
    void addConnectionFailed() throws Exception {
        //Given an email for the connection to add
        String friendEmail = "aaa@mail.fr";

        //When we initiate the request
        mockMvc.perform(post("/transfer/addFriend")
                        .param("friendEmail", friendEmail))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?failedAddConnection"));

    }


    @DisplayName("Try to make a transfer")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void sendMoney() throws Exception {
        //Given a DTO of a person who want to register
        TransferDTO transferDTO = new TransferDTO("lu@mail.fr", new BigDecimal("10.0"), "YO");

        //When we initiate the request
        mockMvc.perform(post("/transfer/transfer-request")
                        .flashAttr("transferDTO", transferDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?successTransfer"));

    }

    @DisplayName("Try to make a transfer but not enough money or your account")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void sendMoneyFailedNotMoney() throws Exception {
        //Given a DTO of a person who want to register
        TransferDTO transferDTO = new TransferDTO("lu@mail.fr", new BigDecimal("1000.0"), "YO");

        //When we initiate the request
        mockMvc.perform(post("/transfer/transfer-request")
                        .flashAttr("transferDTO", transferDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?NotEnoughMoney"));

    }

    @DisplayName("Try to make a transfer")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void sendMoneyFailed() throws Exception {
        //Given a DTO of a person who want to register
        TransferDTO transferDTO = new TransferDTO("aaa@mail.fr", new BigDecimal("10.0"), "YO");

        //When we initiate the request
        mockMvc.perform(post("/transfer/transfer-request")
                        .flashAttr("transferDTO", transferDTO))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?transferFailed"));

    }


}