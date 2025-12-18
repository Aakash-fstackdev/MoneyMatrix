package com.example.MoneyMatrix.dto;

import com.example.MoneyMatrix.entity.Expense;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class ExpenseRequest {

    private Expense.ExpenseCategory category;
    @NotNull
    private String title;
    @NotNull
    private Expense.Type type;
    @NotNull
    private BigDecimal amount;
}
