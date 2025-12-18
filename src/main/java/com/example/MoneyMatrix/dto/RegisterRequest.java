package com.example.MoneyMatrix.dto;

import com.example.MoneyMatrix.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    @NotNull
    private String name;
    @NotNull @Email
    private String email;
    @NotNull
    private String password;
    private User.Role role;
}
