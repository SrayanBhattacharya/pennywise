package com.pennywise.backend.transactions.dto.response;

import com.pennywise.backend.transactions.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        BigDecimal amount,
        String title,
        String merchantName,
        LocalDate transactionDate,
        String notes,
        boolean recurring,
        UUID categoryId,
        String categoryName,
        TransactionType categoryType
) {}
