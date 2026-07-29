package com.pennywise.backend.transactions.controller;

import com.pennywise.backend.transactions.dto.request.CreateTransactionRequest;
import com.pennywise.backend.transactions.dto.response.TransactionResponse;
import com.pennywise.backend.transactions.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "10") int size
    ) {
        return transactionService.getTransactions(page, size);
    }
}
