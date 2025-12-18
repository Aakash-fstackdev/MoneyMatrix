package com.example.MoneyMatrix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Data @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Expense> expenses;
    private Role role;
    public enum Role{
        USER,ADMIN
    }
}
