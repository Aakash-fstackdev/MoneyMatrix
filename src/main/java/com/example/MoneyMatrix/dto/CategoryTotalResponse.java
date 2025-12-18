package com.example.MoneyMatrix.dto;

import com.example.MoneyMatrix.entity.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


public record CategoryTotalResponse (

    Expense.ExpenseCategory category,
            BigDecimal total

){}