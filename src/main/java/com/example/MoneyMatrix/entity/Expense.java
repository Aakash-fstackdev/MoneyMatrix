package com.example.MoneyMatrix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private BigDecimal amount;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Type type=Type.EXPENSE;
    private ExpenseCategory category;
    public enum Type{
        INCOME,EXPENSE
    }
    public enum ExpenseCategory{
        FOOD,RENT,TRAVEL,OTHER
    }
    @PrePersist
    void onCreate(){
        this.createdAt=LocalDateTime.now();
    }
}
