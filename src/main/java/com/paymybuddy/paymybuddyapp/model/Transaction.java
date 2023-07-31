package com.paymybuddy.paymybuddyapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "debtors")
    private Person debtors;

    @ManyToOne
    @JoinColumn(name = "creditors")
    private Person creditors;

    @Column(name = "transferAmount")
    private BigDecimal transferAmount;

    @Column(name = "operationDate")
    private LocalDateTime operationDate;

    @Column(name = "description")
    private String description;


    public Transaction(UUID debtors, UUID creditors, BigDecimal transferAmount, String description) {

        var userTransmitter = new Person();
        userTransmitter.setPersonId(debtors);
        this.debtors = userTransmitter;

        var userBeneficiary = new Person();
        userBeneficiary.setPersonId(creditors);
        this.creditors = userBeneficiary;

        this.transferAmount = transferAmount;
        this.operationDate = LocalDateTime.now();
        this.description = description;
    }


}