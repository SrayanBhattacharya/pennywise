package com.pennywise.backend.auth.service;

import com.pennywise.backend.auth.config.JwtProperties;
import com.pennywise.backend.auth.entity.Role;
import com.pennywise.backend.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(jwtProperties.accessTokenExpiration())))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("type", "refresh")
                .issuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(jwtProperties.refreshTokenExpiration())))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Role extractRole(String token) {
        String role = extractAllClaims(token)
                .get("role", String.class);

        return Role.valueOf(role);
    }

    public UUID extractUserId(String token) {
        String id = extractAllClaims(token)
                .get("userId", String.class);

        return UUID.fromString(id);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String token, User user) {
        return extractUsername(token).equals(user.getEmail())
                && !isTokenExpired(token);
    }

    public long getAccessTokenExpiration() {
        return jwtProperties.accessTokenExpiration();
    }

    public long getRefreshTokenExpiration() {
        return jwtProperties.refreshTokenExpiration();
    }
}
