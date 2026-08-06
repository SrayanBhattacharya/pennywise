package com.pennywise.backend.statement.model;

import com.pennywise.backend.transactions.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParsedTransaction(
        LocalDate transactionDate,
        String description,
        BigDecimal amount,
        TransactionType transactionType,
        BigDecimal balance
) {
}
