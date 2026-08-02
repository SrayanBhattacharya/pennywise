package com.pennywise.backend.transactions.service;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.auth.security.CustomUserDetails;
import com.pennywise.backend.common.exception.ResourceNotFoundException;
import com.pennywise.backend.transactions.dto.request.CreateTransactionRequest;
import com.pennywise.backend.transactions.dto.request.TransactionFilterRequest;
import com.pennywise.backend.transactions.dto.request.UpdateTransactionRequest;
import com.pennywise.backend.transactions.dto.response.TransactionResponse;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.entity.TransactionCategory;
import com.pennywise.backend.transactions.mapper.TransactionMapper;
import com.pennywise.backend.transactions.repository.TransactionCategoryRepository;
import com.pennywise.backend.transactions.repository.TransactionRepository;
import com.pennywise.backend.transactions.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        TransactionCategory category = transactionCategoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction category not found with id: " + request.categoryId()));

        Transaction transaction = transactionMapper.toEntity(request, user, category);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    public Page<TransactionResponse> getTransactions(
            int page,
            int size,
            TransactionFilterRequest filterRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("transactionDate").descending()
        );

        Specification<Transaction> specification = Specification
                .where(TransactionSpecification.hasUser(user))
                .and(TransactionSpecification.notDeleted());

        if (filterRequest.categoryId() != null) {
            specification = specification.and(
                    TransactionSpecification.hasCategory(filterRequest.categoryId())
            );
        }

        if (filterRequest.transactionType() != null) {
            specification = specification.and(
                    TransactionSpecification.hasType(filterRequest.transactionType())
            );
        }

        if (filterRequest.fromDate() != null || filterRequest.toDate() != null) {
            specification = specification.and(
                    TransactionSpecification.hasDateBetween(
                            filterRequest.fromDate(),
                            filterRequest.toDate()
                    )
            );
        }

        if (filterRequest.minAmount() != null || filterRequest.maxAmount() != null) {
            specification = specification.and(
                    TransactionSpecification.hasAmountBetween(
                            filterRequest.minAmount(),
                            filterRequest.maxAmount()
                    )
            );
        }

        if (filterRequest.search() != null && !filterRequest.search().isBlank()) {
            specification = specification.and(
                    TransactionSpecification.containsSearch(filterRequest.search())
            );
        }

        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);

        return transactions.map(transactionMapper::toResponse);
    }

    public TransactionResponse getTransaction(UUID transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        Transaction transaction = transactionRepository
                .findByIdAndUserAndDeletedFalse(transactionId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        return transactionMapper.toResponse(transaction);
    }

    public TransactionResponse updateTransaction(UUID transactionId, UpdateTransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        Transaction transaction = transactionRepository
                .findByIdAndUserAndDeletedFalse(transactionId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        TransactionCategory category = transactionCategoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction category not found with id: " + request.categoryId()));

        transactionMapper.updateEntity(transaction, request, category);
        Transaction updatedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(updatedTransaction);
    }

    public void deleteTransaction(UUID transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        Transaction transaction = transactionRepository
                .findByIdAndUserAndDeletedFalse(transactionId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        transaction.setDeleted(true);
    }
}
