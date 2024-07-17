package com.userregistration.exceptions;

public class InfoAlreadyExistsException extends RuntimeException {
    public InfoAlreadyExistsException(String message){
        super(message);
    }
}
