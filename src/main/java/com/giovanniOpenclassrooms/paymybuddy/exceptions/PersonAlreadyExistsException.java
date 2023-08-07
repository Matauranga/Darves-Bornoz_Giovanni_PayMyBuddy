package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException(Class<?> clazz, String msg) {
        super(clazz.getSimpleName() + " " + msg);
    }
}
