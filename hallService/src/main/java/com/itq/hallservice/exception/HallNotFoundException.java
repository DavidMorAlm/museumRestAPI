package com.itq.hallservice.exception;

public class HallNotFoundException extends RuntimeException{

    public HallNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
