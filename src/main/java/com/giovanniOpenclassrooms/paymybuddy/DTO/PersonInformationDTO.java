package com.giovanniOpenclassrooms.paymybuddy.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonInformationDTO {
    String firstname;
    String lastname;
    String email;
    LocalDate Birthdate;
    BigDecimal accountBalance;
    List<String> friendsList;
}
