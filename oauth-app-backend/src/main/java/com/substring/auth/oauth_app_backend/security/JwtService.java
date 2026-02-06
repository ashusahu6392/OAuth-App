package com.substring.auth.oauth_app_backend.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.substring.auth.oauth_app_backend.entities.Role;
import com.substring.auth.oauth_app_backend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
            @Value("${security.jwt.issuer}") String issuer) {
    	
    	if(secret == null || secret.length() < 64) {
    		throw new IllegalArgumentException("Invalid Secret");
    	}
    	
    	this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;

      
    }
    
    
    public String generateAccessToken(User user) {

        Instant now = Instant.now();

        List<String> roles =
                user.getRoles() == null
                        ? List.of()
                        : user.getRoles()
                              .stream()
                              .map(Role::getName)
                              .collect(Collectors.toList());

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .addClaims(
                        Map.of(
                                "email", user.getEmail(),
                                "roles", roles,
                                "typ", "access"
                        )
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    
    
    public String generateRefreshToken(User user, String jti) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setId(jti)                                 
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")                   
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public Jws<Claims> parse(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw e;   // invalid / expired / tampered token
        }
    }

    
    
    public boolean isAccessToken(String token) {

        Claims claims = parse(token).getBody();

        return "access".equals(claims.get("typ"));
    }
    
    public boolean isRefreshToken(String token) {

        Claims claims = parse(token).getBody();

        return "refresh".equals(claims.get("typ"));
    }

    
    public UUID getUserId(String token) {

        Claims claims = parse(token).getBody();

        return UUID.fromString(claims.getSubject());
    }

    public String getJti(String token) {

        return parse(token).getBody().getId();
    }





}
