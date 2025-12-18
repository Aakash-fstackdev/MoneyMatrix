package com.example.MoneyMatrix.service.serviceImpl;

import com.example.MoneyMatrix.context.AuthContext;
import com.example.MoneyMatrix.dto.*;
import com.example.MoneyMatrix.entity.Expense;
import com.example.MoneyMatrix.entity.User;
import com.example.MoneyMatrix.exceptions.ResourceNotFoundException;
import com.example.MoneyMatrix.exceptions.UserNameNotFoundException;
import com.example.MoneyMatrix.mapper.ExpenseMapper;
import com.example.MoneyMatrix.repository.ExpenseRepo;
import com.example.MoneyMatrix.repository.UserRepo;
import com.example.MoneyMatrix.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.lang.module.ResolutionException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final UserRepo userRepo;
    private final ExpenseRepo expenseRepo;
    private final ExpenseMapper mappers;



    @Override
    public ExpenseResponse createExpense(ExpenseRequest request) {
       Long userId=AuthContext.getId();
        User user=userRepo.findById(userId).orElseThrow(()->{throw new UserNameNotFoundException("user not found");
        });
        Expense expense=new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setTitle(request.getTitle());
        expense.setUser(user);
        expense.setType(request.getType());
        Expense savedExpense=expenseRepo.save(expense);
        return mappers.toResponse(savedExpense);
    }

    public PageResponse<ExpenseResponse> getAllExpenses(int page, int size, String sortBy, String direction) {
        Long id=AuthContext.getId();
        if (size>50)size=50;
        Sort sort=direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<ExpenseResponse> expensePage= expenseRepo.findByUserId(id,pageable).map(mappers::toResponse);
        return new PageResponse<>(
                expensePage.getContent(),
                expensePage.getNumber(),
                expensePage.getSize(),
                expensePage.getTotalElements(),
                expensePage.getTotalPages(),
                expensePage.isLast()
        );
    }


    @Override
    public ExpenseResponse updateExpenseById(Long id, ExpenseRequest request)  {
        Long userId= AuthContext.getId();
      Expense expense= (Expense) expenseRepo.findByIdAndUserId(id,userId).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
expense.setType(request.getType());
expense.setAmount(request.getAmount());
if (request.getCategory()==null){
    expense.setCategory(Expense.ExpenseCategory.OTHER);
}else {
    expense.setCategory(request.getCategory());
}
Expense saved=expenseRepo.save(expense);
return mappers.toResponse(saved);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Long userId= AuthContext.getId();
       Expense expense=expenseRepo.findByIdAndUserId(id,userId).orElseThrow(()->new ResolutionException("Expense Not found"));
        expenseRepo.delete(expense);

    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Long userId=AuthContext.getId();
        Expense expense=expenseRepo.findByIdAndUserId(id,userId).orElseThrow(()->new ResourceNotFoundException("expense not found"));
        return mappers.toResponse(expense);
    }

    @Override
    public Page<ExpenseResponse> getByCategory(Expense.ExpenseCategory category, int page, int size, String sortBy, String direction) {
        Long userId=AuthContext.getId();
        if (size>50)size=50;
        Sort sort=direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page,size);
        return expenseRepo.findByCategoryAndUserId(category,userId,pageable).map(mappers::toResponse);


    }

    @Override
    public Page<PageResponse> getByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        Long userId=AuthContext.getId();
        LocalDateTime start=startDate.atStartOfDay();
        LocalDateTime end=endDate.atTime(23,59,59);
        Pageable pageable=PageRequest.of(page,size,Sort.by("createdAt").descending());
        return expenseRepo.findByUserIdAndCreatedAtBetween(userId,start,end,pageable).map(mappers::toPage);

    }
    public BigDecimal totalExpense(LocalDate startDate,LocalDate endDate){
        Long userId=AuthContext.getId();
        LocalDateTime start=startDate.atStartOfDay();
        LocalDateTime end=endDate.atTime(23,59,59);

        return expenseRepo.getTotalExpense(userId, Expense.Type.EXPENSE,start,end);
    }

    @Override
    public List<MonthlyCategorySummaryResponse> getLast3monthsTotalSummary() {
        Long userId=AuthContext.getId();
        LocalDateTime startDate=LocalDate.now().minusMonths(2).withDayOfMonth(1).atStartOfDay();
        List<Object[]> rows=expenseRepo.findCategoryTotalsLastMonths(userId,startDate);
        Map<String,Map<Expense.ExpenseCategory,BigDecimal>> grouped=new LinkedHashMap<>();
        for (Object[] row:rows){
            String month=(String) row[0];
            Expense.ExpenseCategory category=(Expense.ExpenseCategory) row[1];
            BigDecimal total=(BigDecimal) row[2];

            grouped
                    .computeIfAbsent(month,k ->new EnumMap<>(Expense.ExpenseCategory.class))
                    .put(category,total);

        }
        return grouped.entrySet().stream().map(entry->new MonthlyCategorySummaryResponse(entry.getKey(), entry.getValue().entrySet().stream()
                .map(e ->new CategoryTotalResponse(e.getKey(),e.getValue())).toList())).toList();
    }

    @Override
    public Page<ExpenseResponse> getExpensesDateBetween(LocalDate start, LocalDate end, int page, int size) {
        Long userId=AuthContext.getId();
        Pageable pageable=PageRequest.of(page,size);
        return  expenseRepo.findByUserIdAndCreatedAtBetween(userId,start,end,pageable).map(mappers::toResponse);
    }


}
