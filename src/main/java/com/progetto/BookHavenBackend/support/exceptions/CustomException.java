package com.progetto.BookHavenBackend.support.exceptions;

public class CustomException  extends IllegalArgumentException {
    public CustomException(String msg) {
        super(msg);
    }

}