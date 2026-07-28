package com.pennywise.backend.transactions.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull
        UUID categoryId,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotBlank
        @Size(max = 255)
        String title,

        @Size(max = 150)
        String merchantName,

        @NotNull
        LocalDate transactionDate,

        @Size(max = 500)
        String notes,

        boolean recurring
        ) {
}
