package com.pennywise.backend.transactions.dto.request;

import com.pennywise.backend.transactions.entity.TransactionType;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionFilterRequest(
        UUID categoryId,
        TransactionType transactionType,
        LocalDate fromDate,
        LocalDate toDate,
        @PositiveOrZero
        BigDecimal minAmount,
        @PositiveOrZero
        BigDecimal maxAmount,
        String search
) {
}
