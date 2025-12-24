package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtProperties properties;

    public JwtUtil(JwtProperties properties) {
        this.properties = properties;
    }

    public String generarToken(String email) {

        Key key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());

        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + properties.getExpiration());

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(ahora)
            .setExpiration(expiracion)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
    
}
