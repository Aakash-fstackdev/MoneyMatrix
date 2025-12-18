package com.example.MoneyMatrix.service;

import com.example.MoneyMatrix.dto.LoginRequest;
import com.example.MoneyMatrix.dto.LoginResponse;
import com.example.MoneyMatrix.dto.RegisterRequest;
import com.example.MoneyMatrix.dto.RegisterResponse;

public interface UserService {
    RegisterResponse createUser(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
