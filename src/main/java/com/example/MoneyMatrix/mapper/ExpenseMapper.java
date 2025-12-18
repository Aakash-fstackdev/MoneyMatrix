package com.example.MoneyMatrix.mapper;

import com.example.MoneyMatrix.dto.ExpenseRequest;
import com.example.MoneyMatrix.dto.ExpenseResponse;
import com.example.MoneyMatrix.dto.PageResponse;
import com.example.MoneyMatrix.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE= Mappers.getMapper(ExpenseMapper.class);

    Expense toEntity(ExpenseRequest request);
    ExpenseResponse toResponse(Expense expense);

    PageResponse toPage(Expense expense);
}
