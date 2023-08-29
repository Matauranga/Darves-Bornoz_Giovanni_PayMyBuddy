package com.giovanniOpenclassrooms.paymybuddy.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class UpdatePersonDTO {
    String firstname;
    String lastname;
    String email;
    LocalDate birthdate;

}



