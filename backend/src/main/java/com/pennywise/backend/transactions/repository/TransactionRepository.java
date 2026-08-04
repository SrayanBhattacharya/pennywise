package com.pennywise.backend.transactions.repository;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.dashboard.dto.response.CategoryBreakdownResponse;
import com.pennywise.backend.dashboard.dto.response.MonthlySummaryResponse;
import com.pennywise.backend.transactions.entity.Transaction;
import com.pennywise.backend.transactions.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    Page<Transaction> findByUserAndDeletedFalse(User user, Pageable pageable);
    Optional<Transaction> findByIdAndUserAndDeletedFalse(UUID id, User user);
    List<Transaction> findTop5ByUserAndDeletedFalseOrderByTransactionDateDesc(User user);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
            FROM Transaction t
            WHERE t.user = :user
            AND t.deleted = false
            AND t.category.type = :type
            """)
    BigDecimal getTotalByType(User user, TransactionType type);

    default BigDecimal getTotalIncome(User user) {
        return getTotalByType(user, TransactionType.INCOME);
    }

    default BigDecimal getTotalExpense(User user) {
        return getTotalByType(user, TransactionType.EXPENSE);
    }

    @Query("""
    SELECT new com.pennywise.backend.dashboard.dto.response.CategoryBreakdownResponse(
        t.category.name,
        SUM(t.amount)
    )
    FROM Transaction t
    WHERE t.user = :user
      AND t.deleted = false
      AND t.category.type = :type
    GROUP BY t.category.id, t.category.name
    ORDER BY SUM(t.amount) DESC
    """)
    List<CategoryBreakdownResponse> getCategoryBreakdown(
            @Param("user") User user,
            @Param("type") TransactionType type
    );

    @Query("""
    SELECT new com.pennywise.backend.dashboard.dto.response.MonthlySummaryResponse(
        YEAR(t.transactionDate),
        MONTH(t.transactionDate),
        SUM(
            CASE
                WHEN t.category.type = com.pennywise.backend.transactions.entity.TransactionType.INCOME
                THEN t.amount
                ELSE 0
            END
        ),
        SUM(
            CASE
                WHEN t.category.type = com.pennywise.backend.transactions.entity.TransactionType.EXPENSE
                THEN t.amount
                ELSE 0
            END
        )
    )
    FROM Transaction t
    WHERE t.user = :user
      AND t.deleted = false
    GROUP BY
        YEAR(t.transactionDate),
        MONTH(t.transactionDate)
    ORDER BY
        YEAR(t.transactionDate),
        MONTH(t.transactionDate)
    """)
    List<MonthlySummaryResponse> getMonthlySummary(
            @Param("user") User user
    );
}
