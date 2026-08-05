package com.pennywise.backend.statement.detector;

import com.pennywise.backend.common.exception.UnsupportedStatementFileException;
import com.pennywise.backend.statement.model.StatementFileType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StatementFileTypeDetector {
    public StatementFileType detect(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);

        if (extension == null) {
            throw new UnsupportedStatementFileException("Missing file extension.");
        }

        return switch (extension.toLowerCase()) {
            case "xlsx", "xls" -> StatementFileType.XLSX;

            case "pdf" -> StatementFileType.PDF;

            case "csv" -> StatementFileType.CSV;

            default ->
                    throw new UnsupportedStatementFileException(
                            "Unsupported file type: " + extension
                    );
        };
    }

}
