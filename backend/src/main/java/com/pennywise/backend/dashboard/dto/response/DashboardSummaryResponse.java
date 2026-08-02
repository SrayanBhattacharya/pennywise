package com.pennywise.backend.dashboard.dto.response;

import com.pennywise.backend.transactions.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryResponse(
        BigDecimal currentBalance,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        List<TransactionResponse> recentTransactions
) {
}
