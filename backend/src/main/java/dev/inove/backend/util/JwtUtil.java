package dev.inove.backend.util;

import dev.inove.backend.model.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Utilitário para gerenciar tokens JWT.
 */
@Component
public class JwtUtil {

    private static final String SECRET = "chaveSecretaJWT123chaveSecretaJWT123"; // pelo menos 256 bits (32 caracteres)
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera um token JWT para o usuário.
     * 
     * @param user dados do usuário
     * @return token JWT gerado
     */
    public String generateToken(AuthUser user) {
        LOGGER.info("Gerando token JWT para o usuário: {}", user.getEmail());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("nome", user.getNome())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai o email do token JWT.
     * 
     * @param token token JWT
     * @return email do usuário
     */
    public String extractEmail(String token) {
        LOGGER.info("Extraindo email do token JWT: {}", token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

