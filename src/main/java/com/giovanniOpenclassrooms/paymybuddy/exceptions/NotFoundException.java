package com.giovanniOpenclassrooms.paymybuddy.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> clazz, String msg) {
        super(clazz.getSimpleName() + " " + msg);
    }
}
