package com.pennywise.backend.auth.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
