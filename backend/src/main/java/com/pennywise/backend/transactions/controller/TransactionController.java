package com.pennywise.backend.transactions.controller;

import com.pennywise.backend.transactions.dto.request.CreateTransactionRequest;
import com.pennywise.backend.transactions.dto.request.TransactionFilterRequest;
import com.pennywise.backend.transactions.dto.request.UpdateTransactionRequest;
import com.pennywise.backend.transactions.dto.response.TransactionResponse;
import com.pennywise.backend.transactions.entity.TransactionType;
import com.pennywise.backend.transactions.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) String search
    ) {
        TransactionFilterRequest filterRequest = new TransactionFilterRequest(
                categoryId,
                transactionType,
                fromDate,
                toDate,
                minAmount,
                maxAmount,
                search
        );

        return transactionService.getTransactions(page, size, filterRequest);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable UUID transactionId
    ) {
        return ResponseEntity.ok(
                transactionService.getTransaction(transactionId)
        );
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable UUID transactionId,
            @Valid @RequestBody UpdateTransactionRequest request
    ) {
        return ResponseEntity.ok(
                transactionService.updateTransaction(transactionId, request)
        );
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable UUID transactionId
    ) {
        transactionService.deleteTransaction(transactionId);

        return ResponseEntity.noContent().build();
    }
}
