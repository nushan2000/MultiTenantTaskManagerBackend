package com.example.MultiTenantTaskManagerBackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    // Fixed 32+ char secret key (must be at least 256 bits for HS256)
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "my-super-secure-secret-key-32chars!".getBytes(StandardCharsets.UTF_8)
    );

    // Generate token with username and tenantId
    public String generateToken(String username, Long tenantId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim("tenantId", tenantId)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractTenantId(String token) {
        return extractClaims(token).get("tenantId", Long.class);
    }
}
