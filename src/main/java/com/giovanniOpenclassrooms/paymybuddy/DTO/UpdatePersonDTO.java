package com.giovanniOpenclassrooms.paymybuddy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePersonDTO {
    String firstname;
    String lastname;
    String email;
    LocalDate birthdate;
    String oldPassword;
    String newPassword;
}



