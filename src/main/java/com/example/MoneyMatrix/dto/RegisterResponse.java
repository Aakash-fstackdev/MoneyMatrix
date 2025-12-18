package com.example.MoneyMatrix.dto;

import com.example.MoneyMatrix.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
}
