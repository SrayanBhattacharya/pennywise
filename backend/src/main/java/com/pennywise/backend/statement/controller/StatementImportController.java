package com.pennywise.backend.statement.controller;

import com.pennywise.backend.statement.dto.response.UploadStatementResponse;
import com.pennywise.backend.statement.service.ImportSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementImportController {
    private final ImportSessionService importSessionService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadStatementResponse uploadStatement(@RequestPart("file") MultipartFile file) {
        return importSessionService.createImportSession(file);
    }
}
