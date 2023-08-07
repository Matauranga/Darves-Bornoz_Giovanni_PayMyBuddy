package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class NegativeBalanceAccount extends RuntimeException {
    public NegativeBalanceAccount(String msg) {
        super(msg);
    }
}
