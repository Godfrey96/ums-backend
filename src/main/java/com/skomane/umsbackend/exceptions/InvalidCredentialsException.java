package com.skomane.umsbackend.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super("Invalid email or password entered");
    }
}
