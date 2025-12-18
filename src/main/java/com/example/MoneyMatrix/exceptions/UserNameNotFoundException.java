package com.example.MoneyMatrix.exceptions;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException(String message){
        super(message);
    }
}
