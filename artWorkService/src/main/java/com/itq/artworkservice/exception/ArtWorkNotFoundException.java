package com.itq.artworkservice.exception;

public class ArtWorkNotFoundException extends RuntimeException {
    public ArtWorkNotFoundException(String message) {
        super(message);
    }
}