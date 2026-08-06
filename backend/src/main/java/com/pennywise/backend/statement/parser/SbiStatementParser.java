package com.pennywise.backend.statement.parser;

import com.pennywise.backend.common.exception.StatementParseException;
import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.BankType;
import com.pennywise.backend.statement.model.ParsedTransaction;
import com.pennywise.backend.statement.model.StatementRow;
import com.pennywise.backend.transactions.entity.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SbiStatementParser implements StatementParser{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int findHeaderRow(StatementData statementData) {
        List<StatementRow> rows = statementData.rows();

        for (int i = 0; i < rows.size(); i++) {

            List<String> cells = rows.get(i).cells();

            if (cells.size() < 6) {
                continue;
            }

            if (isHeader(cells)) {
                return i;
            }
        }

        throw new StatementParseException("Unable to locate SBI transaction header.");
    }

    private boolean isHeader(List<String> cells) {

        return equals(cells.get(0), "Date")
                && equals(cells.get(1), "Details")
                && equals(cells.get(3), "Debit")
                && equals(cells.get(4), "Credit")
                && equals(cells.get(5), "Balance");
    }

    private ParsedTransaction parseRow(StatementRow row) {

        List<String> cells = row.cells();

        if (!isTransactionRow(cells)) {
            return null;
        }

        return buildTransaction(cells);
    }

    private ParsedTransaction buildTransaction(List<String> cells) {

        LocalDate transactionDate =
                LocalDate.parse(
                        cells.get(0).trim(),
                        DATE_FORMATTER
                );

        String description = cells.get(1)
                .replace("\n", " ")
                .replace("\r", " ")
                .replaceAll("\\s+", " ")
                .trim();

        String debit =
                cells.get(3).trim();

        String credit =
                cells.get(4).trim();

        TransactionType transactionType;
        BigDecimal amount;

        if (!debit.isBlank()) {

            transactionType = TransactionType.EXPENSE;
            amount = parseMoney(debit);

        } else {

            transactionType = TransactionType.INCOME;
            amount = parseMoney(credit);

        }

        BigDecimal balance =
                parseMoney(cells.get(5));

        return new ParsedTransaction(
                transactionDate,
                description,
                amount,
                transactionType,
                balance
        );
    }

    private BigDecimal parseMoney(String value) {

        value = value.replace(",", "").trim();

        if (value.isBlank()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value);
    }

    private boolean equals(
            String actual,
            String expected
    ) {

        return actual != null
                && actual.trim().equalsIgnoreCase(expected);
    }

    private boolean isTransactionRow(List<String> cells) {

        if (cells.size() < 6) {
            return false;
        }

        if (cells.stream().allMatch(String::isBlank)) {
            return false;
        }

        String date = cells.get(0).trim();

        return date.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    @Override
    public BankType supports() {
        return BankType.SBI;
    }

    @Override
    public List<ParsedTransaction> parse(
            StatementData statementData
    ) {
        int headerIndex = findHeaderRow(statementData);

        List<ParsedTransaction> transactions = new ArrayList<>();

        List<StatementRow> rows = statementData.rows();

        for (int i = headerIndex + 1; i < rows.size(); i++) {

            StatementRow row = rows.get(i);

            ParsedTransaction transaction = parseRow(row);

            if (transaction != null) {
                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
