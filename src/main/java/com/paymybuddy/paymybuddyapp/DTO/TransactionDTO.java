package com.paymybuddy.paymybuddyapp.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
        UUID debtorId,
        UUID creditorId,
        BigDecimal amount,
        String description
) {

}
