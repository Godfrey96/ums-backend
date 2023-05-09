package com.skomane.umsbackend.exceptions;

public class ErrorResponseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ErrorResponseException() {
        super("Your Account is Disabled. Wait for an Admin to activate");
    }
}
