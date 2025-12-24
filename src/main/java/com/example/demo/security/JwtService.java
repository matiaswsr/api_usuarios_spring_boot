package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;   // secret + expiration

    // =============================
    // GENERAR TOKEN
    // =============================
    public String generarToken(String email) {
        return generarToken(email, Map.of());
    }

    public String generarToken(String email, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + properties.getExpiration());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // =============================
    // VALIDAR TOKEN
    // =============================
    public boolean esTokenValido(String token, String email) {
        final String emailToken = obtenerEmail(token);
        return (email.equals(emailToken) && !estaExpirado(token));
    }

    private boolean estaExpirado(String token) {
        return obtenerFechaExpiracion(token).before(new Date());
    }

    // =============================
    // OBTENER DATOS DEL TOKEN
    // =============================
    public String obtenerEmail(String token) {
        return obtenerClaim(token, Claims::getSubject);
    }

    public Date obtenerFechaExpiracion(String token) {
        return obtenerClaim(token, Claims::getExpiration);
    }

    public <T> T obtenerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = obtenerClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims obtenerClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // =============================
    // CLAVE DE FIRMA
    // =============================
    private Key getSigningKey() {
        byte[] keyBytes = properties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
