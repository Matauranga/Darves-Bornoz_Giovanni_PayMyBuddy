package com.giovanniOpenclassrooms.paymybuddy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class TransferDTO {
    private String creditorEmail;
    private BigDecimal amount;
    private String description;

}
