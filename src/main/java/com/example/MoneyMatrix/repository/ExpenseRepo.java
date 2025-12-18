package com.example.MoneyMatrix.repository;

import com.example.MoneyMatrix.dto.CategoryTotalResponse;
import com.example.MoneyMatrix.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepo extends JpaRepository<Expense,Long> {
    Page<Expense> findByUserId(Long id, Pageable pageable);

   Optional< Expense> findByIdAndUserId(Long id, Long userId);

   Page< Expense> findByCategoryAndUserId(Expense.ExpenseCategory category, Long userId,Pageable pageable);

   Page<Expense> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,LocalDateTime end,Pageable pageable);

   @Query("""
           SELECT COALESCE(SUM(e.amount),0)
           From Expense e WHERE e.user.id= :userId And e.type= :type
           AND e.createdAt BETWEEN :start AND :end
           """)
   BigDecimal getTotalExpense(Long userId, Expense.Type type, LocalDateTime start, LocalDateTime end);

   @Query("""
           SELECT 
           FUNCTION('DATE_FORMAT',e.createdAt,'%y-%m')AS month ,e.category,Sum(e.amount)FROM Expense e
           WHERE e.user.id= :userId AND e.createdAt>= :startDate
           GROUP BY month,e.category
           ORDER BY month DESC
           """)
   List<Object[]> findCategoryTotalsLastMonths(Long userId,LocalDateTime startDate);

   Page<Expense> findByUserIdAndCreatedAtBetween(Long userId, LocalDate start,LocalDate end,Pageable pageable);
}
