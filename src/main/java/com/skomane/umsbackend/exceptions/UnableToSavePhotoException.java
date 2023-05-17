package com.skomane.umsbackend.exceptions;

public class UnableToSavePhotoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableToSavePhotoException() {
        super("Unable to save photo");
    }
}
