package com.pennywise.backend.statement.importer;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.statement.model.ParsedTransaction;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.entity.TransactionSource;
import org.springframework.stereotype.Component;

@Component
public class StatementTransactionMapper {
    public Transaction toEntity(ParsedTransaction parsed, User user) {
        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setCategory(null);
        transaction.setAmount(parsed.amount());
        transaction.setTitle(parsed.description());
        transaction.setMerchantName(null);
        transaction.setTransactionDate(parsed.transactionDate());
        transaction.setNotes(null);
        transaction.setRecurring(false);
        transaction.setDeleted(false);
        transaction.setSource(TransactionSource.IMPORTED);

        return transaction;
    }
}
