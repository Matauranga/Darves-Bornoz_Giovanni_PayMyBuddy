package com.giovanniOpenclassrooms.paymybuddy.DTO;

import com.giovanniOpenclassrooms.paymybuddy.model.Person;
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
    String password;
    LocalDate Birthdate;
    BigDecimal accountBalance;
    List<Person> friendsList;
}
