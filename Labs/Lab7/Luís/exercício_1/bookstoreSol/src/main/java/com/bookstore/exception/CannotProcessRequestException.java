package com.bookstore.exception;

public class CannotProcessRequestException extends Exception {

    public CannotProcessRequestException(Exception e) {
        super(e);
    }
}
