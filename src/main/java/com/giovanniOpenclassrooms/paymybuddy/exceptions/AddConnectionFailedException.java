package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class AddConnectionFailedException extends RuntimeException {
    public AddConnectionFailedException(String msg) {
        super(msg);
    }
}