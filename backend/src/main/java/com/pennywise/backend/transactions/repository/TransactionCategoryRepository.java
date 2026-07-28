package com.pennywise.backend.transactions.repository;

import com.pennywise.backend.transactions.entity.TransactionCategory;
import com.pennywise.backend.transactions.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, UUID> {

    List<TransactionCategory> findByType(TransactionType type);
    List<TransactionCategory> findBySystemCategoryTrue();
    Optional<TransactionCategory> findByNameIgnoreCaseAndType(
            String name,
            TransactionType type
    );
}
