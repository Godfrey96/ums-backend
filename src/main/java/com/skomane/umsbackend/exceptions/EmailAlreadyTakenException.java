package com.skomane.umsbackend.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailAlreadyTakenException() {
        super("The email provided is already taken");
    }
}
