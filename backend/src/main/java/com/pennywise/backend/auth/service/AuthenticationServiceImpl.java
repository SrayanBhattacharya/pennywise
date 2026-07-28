package com.pennywise.backend.auth.service;

import com.pennywise.backend.auth.dto.response.AuthResponse;
import com.pennywise.backend.auth.dto.request.LoginRequest;
import com.pennywise.backend.auth.dto.request.RefreshTokenRequest;
import com.pennywise.backend.auth.dto.request.RegisterRequest;
import com.pennywise.backend.auth.entity.RefreshToken;
import com.pennywise.backend.auth.entity.Role;
import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.auth.repository.RefreshTokenRepository;
import com.pennywise.backend.auth.repository.UserRepository;
import com.pennywise.backend.auth.security.CustomUserDetails;
import com.pennywise.backend.common.exception.EmailAlreadyExistsException;
import com.pennywise.backend.common.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists: " + request.email());
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        user = userRepository.save(user);

        return getAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        return getAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken storedToken = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token."));

        if (storedToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new InvalidRefreshTokenException("Refresh token has expired.");
        }

        User user = storedToken.getUser();

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            refreshTokenRepository.delete(storedToken);
            throw new InvalidRefreshTokenException("Invalid refresh token.");
        }

        // Rotate refresh token
        refreshTokenRepository.delete(storedToken);

        return getAuthResponse(user);
    }

    @NonNull
    private AuthResponse getAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(
                        Instant.now().plusMillis(
                                jwtService.getRefreshTokenExpiration()
                        )
                )
                .build();

        refreshTokenRepository.save(newRefreshToken);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpiration()
        );
    }
}
