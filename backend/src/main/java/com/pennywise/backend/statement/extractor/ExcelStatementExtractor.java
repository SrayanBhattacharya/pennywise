package com.pennywise.backend.statement.extractor;

import com.pennywise.backend.common.exception.PasswordRequiredException;
import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.StatementFileType;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class ExcelStatementExtractor implements StatementExtractor {
    @Override
    public StatementFileType supports() {
        return StatementFileType.XLSX;
    }

    @Override
    public StatementData extract(Path file, String password) {
        try (
                Workbook workbook = WorkbookFactory.create(Files.newInputStream(file), password);
        ) {
            return new StatementData(List.of());
        } catch (EncryptedDocumentException e) {
            throw new PasswordRequiredException("Password is required to open the Excel file: " + file.getFileName());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the Excel file: " + file.getFileName(), e);
        }
    }
}
