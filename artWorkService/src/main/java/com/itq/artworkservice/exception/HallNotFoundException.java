package com.itq.artworkservice.exception;

public class HallNotFoundException extends RuntimeException{

    public HallNotFoundException(String message) {
        super(message);
    }

    public HallNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
