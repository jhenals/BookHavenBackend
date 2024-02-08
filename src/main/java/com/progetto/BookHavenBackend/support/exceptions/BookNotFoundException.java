package com.progetto.BookHavenBackend.support.exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException() {}

    public BookNotFoundException(String msg) {
        super(msg);
    }
}
