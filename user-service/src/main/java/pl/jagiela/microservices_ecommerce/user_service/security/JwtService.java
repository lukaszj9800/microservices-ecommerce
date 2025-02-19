package pl.jagiela.microservices_ecommerce.user_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .claims().subject(username).and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key) // 🔹 Poprawiony sposób podpisywania tokenu
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key) // 🔹 Poprawione sprawdzanie podpisu
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}