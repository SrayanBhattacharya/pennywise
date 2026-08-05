package com.pennywise.backend.statement;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

class ExcelEncryptionTest {

    @Test
    void inspectWorkbook() throws Exception {

        Path path = Path.of("../samples/bank-statements/sbi/statement.xlsx");

        try (InputStream inputStream = Files.newInputStream(path)) {

            Workbook workbook = WorkbookFactory.create(inputStream);

            System.out.println("Workbook opened successfully!");
            System.out.println(workbook.getNumberOfSheets());

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }
}
