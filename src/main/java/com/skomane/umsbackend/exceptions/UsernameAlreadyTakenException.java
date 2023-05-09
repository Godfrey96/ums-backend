package com.skomane.umsbackend.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyTakenException() {
        super("The username provided is already taken");
    }
}
