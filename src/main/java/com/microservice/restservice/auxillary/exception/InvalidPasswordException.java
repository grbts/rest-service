package com.microservice.restservice.auxillary.exception;

public class InvalidPasswordException extends RuntimeException  {
    public InvalidPasswordException(String errorMessage){
        super(errorMessage);
    }
}