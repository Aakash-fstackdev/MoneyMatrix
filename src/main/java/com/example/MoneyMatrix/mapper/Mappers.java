package com.example.MoneyMatrix.mapper;

import com.example.MoneyMatrix.dto.ExpenseRequest;
import com.example.MoneyMatrix.dto.ExpenseResponse;
import com.example.MoneyMatrix.dto.RegisterRequest;
import com.example.MoneyMatrix.dto.RegisterResponse;
import com.example.MoneyMatrix.entity.Expense;
import com.example.MoneyMatrix.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Mappers {
    Mappers INSTANCE = org.mapstruct.factory.Mappers.getMapper(Mappers.class);

    User toUser(RegisterRequest request);
    RegisterResponse toResponse(User user);


}
