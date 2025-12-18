package com.example.MoneyMatrix.exceptions;

public class ExpiredJwtException extends RuntimeException{
    public ExpiredJwtException(String message){
        super(message);
    }
}
