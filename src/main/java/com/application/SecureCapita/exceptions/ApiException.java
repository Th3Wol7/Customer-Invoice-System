package com.application.SecureCapita.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
