package com.paymybuddy.paymybuddyapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
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
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "amount_balance")
    private BigDecimal amountBalance;

    @JoinTable(name = "person_friends", joinColumns = {
            @JoinColumn(name = "owner", referencedColumnName = "person_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "friend", referencedColumnName = "person_id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.EAGER)
    List<Person> connectionsList = new ArrayList<>();


    /**
     *
     */
    private String name;


    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "person_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "role_id")
    })
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();


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
