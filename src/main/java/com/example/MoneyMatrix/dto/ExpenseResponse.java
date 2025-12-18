package com.example.MoneyMatrix.dto;

import com.example.MoneyMatrix.entity.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String category;
    private String title;
    private Expense.Type type;
    private BigDecimal amount;
    private LocalDateTime createdAt;


}
