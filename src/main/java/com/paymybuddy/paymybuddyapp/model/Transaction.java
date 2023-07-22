package com.paymybuddy.paymybuddyapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    @JoinColumn(name = "transmitter")
    private User transmitter;
    @ManyToOne
    @JoinColumn(name = "beneficiary")
    private User beneficiary;
    @Column(name = "transfer_amount")
    private Double transferAmount;
    @Column(name = "operation_date")
    private Date operationDate;
    @Column(name = "description")
    private String description;


}
