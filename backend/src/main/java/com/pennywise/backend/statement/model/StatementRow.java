package com.pennywise.backend.statement.model;

import java.util.List;

public record StatementRow(
        List<String> cells
) {
}