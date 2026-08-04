package com.pennywise.backend.dashboard.dto.response;

import java.math.BigDecimal;

public record MonthlySummaryResponse(
        Integer year,
        Integer month,
        BigDecimal income,
        BigDecimal expense
) {
}
