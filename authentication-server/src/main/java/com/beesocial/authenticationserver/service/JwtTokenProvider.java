package com.beesocial.authenticationserver.service;

import com.beesocial.authenticationserver.DTOs.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret; // A 256-bit key for HS256

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    // Generate JWT Token
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        byte[] secretKeyBytes = jwtSecret.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get user email from the token
    public String getUserEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()                // Use parserBuilder()
                .setSigningKey(jwtSecret)                  // Set the signing key
                .build()                                   // Build the parser
                .parseClaimsJws(token)                     // Parse the token
                .getBody();                                // Get the claims from the token

        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        UserDetails userDetails = new User(username, "");

        return new UsernamePasswordAuthenticationToken(userDetails, token);
    }
}
