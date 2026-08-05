package com.pennywise.backend.statement.controller;

import com.pennywise.backend.statement.dto.request.UnlockStatementRequest;
import com.pennywise.backend.statement.dto.response.UploadStatementResponse;
import com.pennywise.backend.statement.service.ImportSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementImportController {
    private final ImportSessionService importSessionService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadStatementResponse uploadStatement(@RequestPart("file") MultipartFile file) {
        return importSessionService.createImportSession(file);
    }

//    @PostMapping("/{importId}/unlock")
//    public UploadStatementResponse unlockStatement(
//            @PathVariable UUID importId,
//            @Valid @RequestBody UnlockStatementRequest request
//    ) {
//        return importSessionService.unlockStatement(
//                importId,
//                request.password()
//        );
//    }
}
