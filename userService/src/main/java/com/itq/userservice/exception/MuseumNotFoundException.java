package com.itq.userservice.exception;


public class MuseumNotFoundException extends RuntimeException {
    public MuseumNotFoundException(String message) {
        super(message);
    }
}