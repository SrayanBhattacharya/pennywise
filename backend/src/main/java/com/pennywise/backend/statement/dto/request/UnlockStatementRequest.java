package com.pennywise.backend.statement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UnlockStatementRequest(

        @NotBlank(message = "Password is required.")
        String password

) {
}
