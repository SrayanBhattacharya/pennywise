package com.pennywise.backend.statement.domain;

import java.util.List;

public record StatementData(
        String sheetName,
        List<List<String>> rows
) {
}
