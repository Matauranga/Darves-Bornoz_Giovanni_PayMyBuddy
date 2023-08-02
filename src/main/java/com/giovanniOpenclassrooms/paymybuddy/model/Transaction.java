package com.giovanniOpenclassrooms.paymybuddy.model;

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
    @JoinColumn(name = "debtor")
    private Person debtor;

    @ManyToOne
    @JoinColumn(name = "creditor")
    private Person creditor;

    @Column(name = "transferAmount")
    private BigDecimal transferAmount;

    @Column(name = "operationDate")
    private LocalDateTime operationDate;

    @Column(name = "description")
    private String description;


    public Transaction(UUID debtors, UUID creditors, BigDecimal transferAmount, String description) {

        var userTransmitter = new Person();
        userTransmitter.setPersonId(debtors);
        this.debtor = userTransmitter;

        var userBeneficiary = new Person();
        userBeneficiary.setPersonId(creditors);
        this.creditor = userBeneficiary;

        this.transferAmount = transferAmount;
        this.operationDate = LocalDateTime.now();
        this.description = description;
    }


}