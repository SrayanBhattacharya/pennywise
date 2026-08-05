package com.pennywise.backend.statement.extractor;

import com.pennywise.backend.common.exception.PasswordRequiredException;
import com.pennywise.backend.common.exception.StatementExtractionException;
import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.model.StatementFileType;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
                InputStream inputStream = Files.newInputStream(file);
                Workbook workbook = WorkbookFactory.create(inputStream, password)
        ) {
            Sheet sheet = workbook.getSheetAt(0);

            List<List<String>> rows = new ArrayList<>();

            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                List<String> cells = new ArrayList<>();

                for (Cell cell : row) {
                    cells.add(formatter.formatCellValue(cell));
                }

                rows.add(cells);
            }

            return new StatementData(
                    sheet.getSheetName(),
                    rows
            );
        } catch (EncryptedDocumentException e) {
            throw new PasswordRequiredException("Password is required to open the Excel file: " + file.getFileName());
        } catch (IOException e) {
            throw new StatementExtractionException("Failed to extract data from Excel file: " + file.getFileName() + ". Error: " + e.getMessage());
        }
    }
}
