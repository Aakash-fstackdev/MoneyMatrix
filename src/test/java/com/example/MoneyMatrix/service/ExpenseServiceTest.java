package com.example.MoneyMatrix.service;

import com.example.MoneyMatrix.dto.ExpenseResponse;
import com.example.MoneyMatrix.dto.PageResponse;
import com.example.MoneyMatrix.entity.Expense;
import com.example.MoneyMatrix.entity.User;
import com.example.MoneyMatrix.mapper.ExpenseMapper;
import com.example.MoneyMatrix.repository.ExpenseRepo;
import com.example.MoneyMatrix.security.CustomUserDetails;
import com.example.MoneyMatrix.service.serviceImpl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepo expenseRepo;

    @Mock
    private ExpenseMapper expenseMapper;
    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void contextLoads(){
        assertNotNull(expenseService);
    }

    @Test
    void shouldReturnExpenseBetweenDates(){
        Long userId=1L;
        LocalDate start=LocalDate.of(2025,12,1);
        LocalDate end=LocalDate.of(2025,12,31);
        Expense expense=new Expense();
        expense.setId(1L);
        expense.setAmount(BigDecimal.valueOf(1000));
        expense.setCategory(Expense.ExpenseCategory.FOOD);
        ExpenseResponse expenseResponse=new ExpenseResponse();
        expenseResponse.setId(1L);
        Page<Expense> page=new PageImpl<>(List.of(expense));

        Mockito.when(expenseRepo.findByUserIdAndCreatedAtBetween(Mockito.anyLong(), (LocalDateTime) Mockito.any(),Mockito.any(),Mockito.any(Pageable.class))).thenReturn(page);

        Page<ExpenseResponse> result=expenseService.getExpensesDateBetween(start,end,0,10);
        assertEquals(1,result.getTotalElements());
    }
    @BeforeEach
    void setupSecurity() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRole(User.Role.USER);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
    }


}
