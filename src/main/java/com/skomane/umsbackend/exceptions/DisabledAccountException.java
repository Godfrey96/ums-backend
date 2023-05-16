package com.skomane.umsbackend.exceptions;

public class DisabledAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DisabledAccountException() {
        super("Your Account is Disabled. Wait for an Admin to activate");
    }
}
