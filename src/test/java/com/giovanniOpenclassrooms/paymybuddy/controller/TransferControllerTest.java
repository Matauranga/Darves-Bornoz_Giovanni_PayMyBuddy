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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        String friendEmail = "a@mail.fr";

        //When we initiate the request
        mockMvc.perform(post("/transfer/add-friend")
                        .param("friendEmail", friendEmail))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Congrats you have a new friend!")));
    }

    @DisplayName("Try to add a connection but failed")
    @Test
    @WithMockUser(username = "g@mail.fr", password = "$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG")
    void addConnectionFailed() throws Exception {
        //Given an email for the connection to add
        String friendEmail = "aaa@mail.fr";

        //When we initiate the request
        mockMvc.perform(post("/transfer/add-friend")
                        .param("friendEmail", friendEmail))
                .andDo(MockMvcResultHandlers.print())

                //Then we verify is all works correctly
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("An error occurred, please verify the email.")));

    }

    @DisplayName("Try to make a transfer and it's ok")
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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Congrats you have lost money!")));

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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You don't have enough money in your account, fill it up or change the amount of your transfer.")));

    }

    @DisplayName("Try to make a transfer but failed")
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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Oops something went wrong, please try again!")));

    }


}