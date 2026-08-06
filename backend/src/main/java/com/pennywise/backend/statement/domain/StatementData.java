package com.pennywise.backend.statement.domain;

import com.pennywise.backend.statement.model.StatementRow;

import java.util.List;

public record StatementData(
        String sheetName,
        List<StatementRow> rows
) {
}
