package com.pennywise.backend.statement.extractor;

import com.pennywise.backend.common.exception.UnsupportedStatementFileException;
import com.pennywise.backend.statement.model.StatementFileType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class StatementExtractorFactory {
    private final Map<StatementFileType, StatementExtractor> extractors;

    public StatementExtractorFactory(List<StatementExtractor> extractors) {
        this.extractors = new EnumMap<>(StatementFileType.class);

        for (StatementExtractor extractor : extractors) {
            this.extractors.put(extractor.supports(), extractor);
        }
    }

    public StatementExtractor getExtractor(StatementFileType fileType) {
        StatementExtractor extractor = extractors.get(fileType);

        if (extractor == null) {
            throw new UnsupportedStatementFileException(
                    "No extractor found for file type: " + fileType
            );
        }

        return extractor;
    }

}