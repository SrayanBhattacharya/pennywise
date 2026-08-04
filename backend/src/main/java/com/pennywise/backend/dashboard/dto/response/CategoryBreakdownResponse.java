package com.pennywise.backend.dashboard.dto.response;

import java.math.BigDecimal;

public record CategoryBreakdownResponse(
        String categoryName,
        BigDecimal totalAmount
) {
}
