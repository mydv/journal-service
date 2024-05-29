package com.mohity.journalservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtValidationService {
    private String SECRET_KEY = "24baa20b8a22451b80923d75ef3b6ce6d3eedad59be287e14ecf4e7d2c1c836d";

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> (Integer) claims.get("userId")).longValue();
    }

    public boolean isValid(String token, Long requestUserId) {
        Long tokenUserId = extractUserId(token);

        if (hasAdminRole(token)) {
            return !isTokenExpired(token);
        } else {
            return !isTokenExpired(token) && requestUserId.equals(tokenUserId);
        }
    }

    private Boolean hasAdminRole(String token) {
        String tokenRole = extractClaim(token, claims -> (String) claims.get("role"));
        return tokenRole.equals("ADMIN");
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}