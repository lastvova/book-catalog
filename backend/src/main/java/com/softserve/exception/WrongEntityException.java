package com.softserve.exception;

public class WrongEntityException extends RuntimeException{

    public WrongEntityException(String message) {
        super(message);
    }
}
