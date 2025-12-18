package com.example.MoneyMatrix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<?> handleUserNameExists(UserNameAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("status",409,"error",ex.getMessage()));
    }
    @ExceptionHandler(AccessDeniedCustomExceptions.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedCustomExceptions ex){
        Map<String,String> error=new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status",404,"error",ex.getMessage()));
    }
}
