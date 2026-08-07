package com.pennywise.backend.statement.service;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.statement.importer.StatementTransactionMapper;
import com.pennywise.backend.statement.model.ParsedTransaction;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementImportService {
    private final TransactionRepository transactionRepository;
    private final StatementTransactionMapper transactionMapper;

    public void importTransaction(List<ParsedTransaction> parsedTransactions, User user) {
        List<Transaction> transactions = parsedTransactions.stream()
                .map(parsed -> transactionMapper.toEntity(parsed, user))
                .toList();

        transactionRepository.saveAll(transactions);
    }
}
