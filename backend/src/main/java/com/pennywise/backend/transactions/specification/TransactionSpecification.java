package com.pennywise.backend.transactions.specification;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.entity.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class TransactionSpecification {
    private TransactionSpecification() {
    }

    public static Specification<Transaction> hasUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    public static Specification<Transaction> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted"));
    }

    public static Specification<Transaction> hasCategory(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Transaction> hasType(TransactionType type) {
        if (type == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("type"), type);
    }

    public static Specification<Transaction> hasDateBetween(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null && toDate == null) {
            return null;
        }

        if (fromDate != null && toDate != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                    root.get("transactionDate"),
                    fromDate,
                    toDate
            );
        }

        if (fromDate != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                    root.get("transactionDate"),
                    fromDate
            );
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                root.get("transactionDate"),
                toDate
        );
    }

    public static Specification<Transaction> hasAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        if (minAmount == null && maxAmount == null) {
            return null;
        }

        if (minAmount != null && maxAmount != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                    root.get("amount"),
                    minAmount,
                    maxAmount
            );
        }

        if (minAmount != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                    root.get("amount"),
                    minAmount
            );
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                root.get("amount"),
                maxAmount
        );
    }

    public static Specification<Transaction> containsSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }

        String pattern = "%" + search.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                criteriaBuilder.like(
                        criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("merchantName"), "")),
                        pattern
                )
        );
    }
}
