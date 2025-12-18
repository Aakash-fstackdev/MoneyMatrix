package com.example.MoneyMatrix.service;

import com.example.MoneyMatrix.dto.*;
import com.example.MoneyMatrix.entity.Expense;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseResponse createExpense(ExpenseRequest request);
   PageResponse<ExpenseResponse> getAllExpenses(int page, int size, String sortBy, String direction);
    ExpenseResponse updateExpenseById(Long id,ExpenseRequest request);
    void deleteExpenseById(Long id);
    ExpenseResponse getExpenseById(Long id);

    Page<ExpenseResponse> getByCategory(Expense.ExpenseCategory category, int page, int size, String sortBy, String direction);

    Page<PageResponse> getByDateRange(LocalDate startDate, LocalDate end, int page, int size);
    BigDecimal totalExpense(LocalDate startDate, LocalDate endDate);
    List<MonthlyCategorySummaryResponse> getLast3monthsTotalSummary();


    Page<ExpenseResponse> getExpensesDateBetween(LocalDate start, LocalDate end, int page, int size);
}
