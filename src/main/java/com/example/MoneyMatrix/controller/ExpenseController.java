package com.example.MoneyMatrix.controller;

import com.example.MoneyMatrix.context.AuthContext;
import com.example.MoneyMatrix.dto.ExpenseRequest;
import com.example.MoneyMatrix.dto.ExpenseResponse;
import com.example.MoneyMatrix.dto.MonthlyCategorySummaryResponse;
import com.example.MoneyMatrix.dto.PageResponse;
import com.example.MoneyMatrix.entity.Expense;
import com.example.MoneyMatrix.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;




    @PostMapping("/expenses")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest request){
        ExpenseResponse response=expenseService.createExpense(request);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/expenses/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id,@RequestBody ExpenseRequest request){
        ExpenseResponse response=expenseService.updateExpenseById(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/expenses/{id}")
    public  ResponseEntity<?> deleteExpense(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
        return ResponseEntity.ok("Deleted Successfully");

    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }


    @GetMapping("/expenses")
    public ResponseEntity<?> getAllExpenses(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "createdAt")String sortBy,
                                            @RequestParam(defaultValue = "desc")String direction){
        return ResponseEntity.ok(expenseService.getAllExpenses(page, size, sortBy, direction));
    }

   @GetMapping("/expenses/category")
    public ResponseEntity<?> getByCategory(@RequestParam Expense.ExpenseCategory category,@RequestParam(defaultValue = "0")int page,
                                           @RequestParam(defaultValue = "10")int size,
                                           @RequestParam(defaultValue = "createdAt")String sortBy,
                                           @RequestParam(defaultValue = "desc")String direction){
        return ResponseEntity.ok(expenseService.getByCategory(category, page, size, sortBy, direction));
   }
   @GetMapping("/expenses/range")
    public Page<PageResponse> getByRange(@RequestParam LocalDate start, @RequestParam LocalDate end, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        return  expenseService.getByDateRange(start, end, page, size);
   }
   @GetMapping("/expense/summary/total")
    public BigDecimal totalExpense(@RequestParam LocalDate start, @RequestParam LocalDate end){
        return expenseService.totalExpense(start,end);
   }
  @GetMapping("/expenses/summary/category/last3months")
    public ResponseEntity<List<MonthlyCategorySummaryResponse>> getCategorySummary(){
        return ResponseEntity.ok(expenseService.getLast3monthsTotalSummary());
  }
  @Operation(summary = "Get Expenses By Date Range",description = "Fetch paginated expenses between start and end date")
  @GetMapping("/expenses/dateBetween")
    public Page getByDateRange(@RequestParam LocalDate start,@RequestParam LocalDate end,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){
        return expenseService.getExpensesDateBetween(start, end,page,size);
  }
}
