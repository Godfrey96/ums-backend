package com.skomane.umsbackend.exceptions;

public class UnableToResolvePhotoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableToResolvePhotoException() {
        super("The photo you are looking for cannot be found");
    }
}
