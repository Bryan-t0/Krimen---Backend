package com.bryan.tienda.security;

import com.bryan.tienda.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final Key secretKey;
    private final long expirationMs;

    public JwtService(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.expiration}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().name());

        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getEmail())
                .setIssuedAt(ahora)
                .setExpiration(expira)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, String email) {
        String emailToken = obtenerEmail(token);
        return emailToken.equals(email) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        Date expiracion = getAllClaims(token).getExpiration();
        return expiracion.before(new Date());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
