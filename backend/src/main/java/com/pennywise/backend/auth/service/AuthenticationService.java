package com.pennywise.backend.auth.service;

import com.pennywise.backend.auth.dto.response.AuthResponse;
import com.pennywise.backend.auth.dto.request.LoginRequest;
import com.pennywise.backend.auth.dto.request.RefreshTokenRequest;
import com.pennywise.backend.auth.dto.request.RegisterRequest;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}
