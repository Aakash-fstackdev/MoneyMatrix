package com.example.MoneyMatrix.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException{
    public UserNameAlreadyExistsException(String message){
        super(message);
    }
}
