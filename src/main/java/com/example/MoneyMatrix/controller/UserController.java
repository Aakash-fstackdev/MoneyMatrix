package com.example.MoneyMatrix.controller;

import com.example.MoneyMatrix.dto.LoginRequest;
import com.example.MoneyMatrix.dto.LoginResponse;
import com.example.MoneyMatrix.dto.RegisterRequest;
import com.example.MoneyMatrix.dto.RegisterResponse;
import com.example.MoneyMatrix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request){
        RegisterResponse response=userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        LoginResponse response=userService.login(request);
        return ResponseEntity.ok(response);
    }
}
