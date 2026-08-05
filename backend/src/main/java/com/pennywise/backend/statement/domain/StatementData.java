package com.pennywise.backend.statement.domain;

import java.util.List;

public record StatementData(
        List<List<String>> rows
) {
}
