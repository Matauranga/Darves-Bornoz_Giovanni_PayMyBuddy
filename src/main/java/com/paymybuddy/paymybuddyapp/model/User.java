package com.paymybuddy.paymybuddyapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "mdp")
    private String mdp;
    @Column(name = "amount_balance")
    private Double amountBalance;

    @JoinTable(name = "user_friends", joinColumns = {
            @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "friend", referencedColumnName = "user_id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.EAGER)
    List<User> connectionsList = new ArrayList<>();

}
