package com.application.SecureCapita.Exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
