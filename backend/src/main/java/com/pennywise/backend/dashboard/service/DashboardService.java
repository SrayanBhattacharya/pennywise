package com.pennywise.backend.dashboard.service;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.common.service.CurrentUserService;
import com.pennywise.backend.dashboard.dto.response.DashboardSummaryResponse;
import com.pennywise.backend.transactions.dto.response.TransactionResponse;
import com.pennywise.backend.transactions.mapper.TransactionMapper;
import com.pennywise.backend.transactions.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final CurrentUserService currentUserService;

    public DashboardSummaryResponse getDashboardSummary() {

        User user = currentUserService.getCurrentUser();

        BigDecimal totalIncome = transactionRepository.getTotalIncome(user);
        BigDecimal totalExpense = transactionRepository.getTotalExpense(user);

        BigDecimal currentBalance = totalIncome.subtract(totalExpense);

        List<TransactionResponse> recentTransactions = transactionRepository
                .findTop5ByUserAndDeletedFalseOrderByTransactionDateDesc(user)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        return new DashboardSummaryResponse(
                currentBalance,
                totalIncome,
                totalExpense,
                recentTransactions
        );
    }
}
