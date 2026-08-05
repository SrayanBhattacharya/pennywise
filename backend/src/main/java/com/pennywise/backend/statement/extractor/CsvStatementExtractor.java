package com.pennywise.backend.statement.extractor;

import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.StatementFileType;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class CsvStatementExtractor implements StatementExtractor {

    @Override
    public StatementFileType supports() {
        return StatementFileType.CSV;
    }

    @Override
    public StatementData extract(Path file, String password) {
        throw new UnsupportedOperationException(
                "CSV statement extraction is not implemented yet."
        );
    }
}
