package com.giovanniOpenclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "person_id")
    private UUID personId;

    private String firstname;

    private String lastname;

    private LocalDate birthdate;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(name = "amount_balance", nullable = false)
    private BigDecimal amountBalance = BigDecimal.ZERO;

    @JoinTable(name = "person_friends", joinColumns = {
            @JoinColumn(name = "owner", referencedColumnName = "person_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "friend", referencedColumnName = "person_id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.EAGER)
    List<Person> connectionsList = new ArrayList<>();

    /**
     * @param amount
     * @return
     */
    public Person creditBalance(BigDecimal amount) {
        amountBalance = amountBalance.add(amount);
        return this;
    }

    public Person debitBalance(BigDecimal amount) {

        if (amountBalance.subtract(amount).doubleValue() < 0) {
            throw new RuntimeException("t'as pas de fric gros");
        }

        amountBalance = amountBalance.subtract(amount);
        return this;


    }

    public Person(String firstname, String lastname, String email, String password, BigDecimal amountBalance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.amountBalance = amountBalance;
    }
}
