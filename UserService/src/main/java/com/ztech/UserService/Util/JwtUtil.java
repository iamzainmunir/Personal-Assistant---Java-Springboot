package com.ztech.UserService.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String username, String email, String role) {
        Key signingKey = getSigningKey();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now())) // Use `Instant` for modern date/time APIs
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(expirationTime)))) // Expiration based on `Instant`
                .signWith(signingKey) // Simplified `signWith` without the algorithm parameter
                .claim("username", username) // Use `claim` instead of `addClaims`
                .claim("email", email)
                .claim("role", role)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        Key signingKey = getSigningKey();
        return Jwts.parser() // Use `parserBuilder` instead of `parser`
                .setSigningKey(signingKey) // Updated method for setting the signing key
                .build()
                .parseClaimsJws(token) // `parseClaimsJws` is still used here
                .getBody();
    }
}
