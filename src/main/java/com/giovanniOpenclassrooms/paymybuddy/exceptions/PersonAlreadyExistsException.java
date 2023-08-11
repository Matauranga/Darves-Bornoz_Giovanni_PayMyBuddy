package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException(String msg) {
        super(msg);
    }
}
