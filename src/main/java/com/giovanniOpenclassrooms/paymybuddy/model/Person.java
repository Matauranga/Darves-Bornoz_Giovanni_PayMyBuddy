package com.giovanniOpenclassrooms.paymybuddy.model;

import com.giovanniOpenclassrooms.paymybuddy.exceptions.NegativeBalanceAccountException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

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
    private UUID id;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Person> connectionsList = new ArrayList<>();


    /**
     * @param amount The amount to credit
     * @return the new amount
     */
    public Person creditBalance(BigDecimal amount) {
        amountBalance = amountBalance.add(amount);
        return this;
    }


    /**
     *
     * @param amount The amount to debit
     * @return the new amount
     */
    public Person debitBalance(BigDecimal amount) {

        if (amountBalance.subtract(amount).doubleValue() < 0) {
            throw new NegativeBalanceAccountException("t'as pas de fric gros");
        }

        amountBalance = amountBalance.subtract(amount);
        return this;
    }

}
