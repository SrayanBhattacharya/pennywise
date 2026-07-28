package com.pennywise.backend.transactions.entity;

import com.pennywise.backend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "transaction_categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_transaction_category_name_type",
                        columnNames = {"name", "type"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class TransactionCategory extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(length = 50)
    private String icon;

    @Column(length = 20)
    private String colour;

    @Column(nullable = false)
    private boolean systemCategory = true;
}
