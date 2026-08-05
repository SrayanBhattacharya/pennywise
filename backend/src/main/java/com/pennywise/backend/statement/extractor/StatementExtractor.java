package com.pennywise.backend.statement.extractor;

import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.StatementFileType;

import java.nio.file.Path;

public interface StatementExtractor {
    StatementFileType supports();

    StatementData extract(Path file, String password);
}
