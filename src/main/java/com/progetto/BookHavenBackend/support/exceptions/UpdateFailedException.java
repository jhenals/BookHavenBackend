package com.progetto.BookHavenBackend.support.exceptions;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String message) {
        super(message);
    }
}
