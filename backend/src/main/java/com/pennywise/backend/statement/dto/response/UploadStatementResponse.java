package com.pennywise.backend.statement.dto.response;

import com.pennywise.backend.statement.entity.ImportStatus;

import java.util.UUID;

public record UploadStatementResponse(
        UUID importId,
        ImportStatus status
) {
}
