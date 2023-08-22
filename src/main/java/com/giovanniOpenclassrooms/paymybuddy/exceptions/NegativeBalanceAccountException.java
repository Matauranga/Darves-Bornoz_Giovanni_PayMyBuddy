package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class NegativeBalanceAccountException extends RuntimeException {
    public NegativeBalanceAccountException(String msg) {
        super(msg);
    }
}
