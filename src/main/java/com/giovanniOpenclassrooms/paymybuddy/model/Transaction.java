package com.giovanniOpenclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "debtor")
    private Person debtor;

    @ManyToOne
    @JoinColumn(name = "creditor")
    private Person creditor;

    @Column(name = "transfer_amount")
    private BigDecimal transferAmount;

    private BigDecimal taxAmount;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    private String description;


    public Transaction(Person debtor, Person creditor, BigDecimal transferAmount,BigDecimal taxAmount, String description) {

        this.debtor = debtor;
        this.creditor = creditor;
        this.transferAmount = transferAmount;
        this.taxAmount = taxAmount;
        this.operationDate = LocalDateTime.now();
        this.description = description;
    }

    public Transaction(Person person, BigDecimal transferAmount, String description) {

        this.debtor = person;
        this.creditor = person;
        this.transferAmount = transferAmount;
        this.operationDate = LocalDateTime.now();
        this.description = description;
    }
}