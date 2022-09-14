package com.microservice.restservice.auxillary.exception;

public class InvalidEmailException extends RuntimeException  {
    public InvalidEmailException(String errorMessage){
        super(errorMessage);
    }

}
