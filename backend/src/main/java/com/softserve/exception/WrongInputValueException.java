package com.softserve.exception;

public class WrongInputValueException extends RuntimeException{
    public WrongInputValueException(String message) {
        super(message);
    }
}
