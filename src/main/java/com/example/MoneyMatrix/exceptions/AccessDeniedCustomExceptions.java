package com.example.MoneyMatrix.exceptions;

import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedCustomExceptions extends RuntimeException {
    public AccessDeniedCustomExceptions(String message){
        super(message);
    }
}
