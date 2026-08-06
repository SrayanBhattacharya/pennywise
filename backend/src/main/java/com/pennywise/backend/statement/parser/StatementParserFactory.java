package com.pennywise.backend.statement.parser;

import com.pennywise.backend.common.exception.UnsupportedBankException;
import com.pennywise.backend.statement.model.BankType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class StatementParserFactory {
    private final Map<BankType, StatementParser> parsers;

    public StatementParserFactory(List<StatementParser> parsers) {
        this.parsers = new EnumMap<>(BankType.class);

        for (StatementParser parser : parsers) {
            this.parsers.put(parser.supports(), parser);
        }
    }

    public StatementParser getParser(BankType bankType) {

        StatementParser parser = parsers.get(bankType);

        if (parser == null) {
            throw new UnsupportedBankException("Unsupported bank: " + bankType);
        }

        return parser;
    }
}
