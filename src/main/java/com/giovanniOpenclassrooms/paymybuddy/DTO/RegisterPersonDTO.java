package com.giovanniOpenclassrooms.paymybuddy.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPersonDTO {

    @NotEmpty(message = "FirstName should not be empty")
    private String firstName;

    @NotEmpty(message = "LastName should not be empty")
    private String lastName;

    @NotEmpty(message = "Birthdate should not be empty")
    private String birthdate;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

}