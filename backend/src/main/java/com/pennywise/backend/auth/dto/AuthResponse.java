package com.pennywise.backend.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
