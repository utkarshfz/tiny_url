package com.demo.tiny_url.exception.model;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }

}
