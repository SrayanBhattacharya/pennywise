package com.pennywise.backend.statement.parser;

import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.BankType;
import com.pennywise.backend.statement.model.ParsedTransaction;

import java.util.List;

public interface StatementParser {
    BankType supports();
    List<ParsedTransaction> parse(StatementData statementData);
}
