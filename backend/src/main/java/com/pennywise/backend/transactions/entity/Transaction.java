package com.pennywise.backend.transactions.entity;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(name = "idx_transaction_user", columnList = "user_id"),
                @Index(name = "idx_transaction_date", columnList = "transaction_date"),
                @Index(name = "idx_transaction_category", columnList = "category_id"),
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_category")
    )
    private TransactionCategory category;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 150)
    private String merchantName;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private boolean recurring = false;

    @Column(nullable = false)
    private boolean deleted = false;
}
