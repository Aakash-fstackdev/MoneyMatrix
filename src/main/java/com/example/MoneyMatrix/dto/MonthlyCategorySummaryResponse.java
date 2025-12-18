package com.example.MoneyMatrix.dto;

import java.util.List;

public record MonthlyCategorySummaryResponse(
        String month, List<CategoryTotalResponse> categories
        ) {
}
