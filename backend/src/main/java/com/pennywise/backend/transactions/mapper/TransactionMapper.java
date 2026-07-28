package com.pennywise.backend.transactions.mapper;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.transactions.dto.request.CreateTransactionRequest;
import com.pennywise.backend.transactions.dto.response.TransactionResponse;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.entity.TransactionCategory;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTitle(),
                transaction.getMerchantName(),
                transaction.getTransactionDate(),
                transaction.getNotes(),
                transaction.isRecurring(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName(),
                transaction.getCategory().getType(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    public Transaction toEntity(
            CreateTransactionRequest request,
            User user,
            TransactionCategory category
    ) {
        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setCategory(category);

        transaction.setAmount(request.amount());
        transaction.setTitle(request.title());
        transaction.setMerchantName(request.merchantName());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setNotes(request.notes());
        transaction.setRecurring(request.recurring());

        return transaction;
    }

//    public void updateEntity(
//            Transaction transaction,
//            UpdateTransactionRequest request,
//            TransactionCategory category
//    ) {
//        transaction.setCategory(category);
//        transaction.setAmount(request.amount());
//        transaction.setTitle(request.title());
//        transaction.setMerchantName(request.merchantName());
//        transaction.setTransactionDate(request.transactionDate());
//        transaction.setNotes(request.notes());
//        transaction.setRecurring(request.recurring());
//    }
}
