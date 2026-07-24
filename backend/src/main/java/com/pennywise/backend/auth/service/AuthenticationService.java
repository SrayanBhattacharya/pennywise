package com.pennywise.backend.auth.service;

import com.pennywise.backend.auth.dto.AuthResponse;
import com.pennywise.backend.auth.dto.LoginRequest;
import com.pennywise.backend.auth.dto.RefreshTokenRequest;
import com.pennywise.backend.auth.dto.RegisterRequest;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}
