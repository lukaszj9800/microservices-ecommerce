package pl.jagiela.microservices_ecommerce.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.jagiela.microservices_ecommerce.api_gateway.client.UserServiceClient;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtAuthManager implements ReactiveAuthenticationManager {
    private final SecretKey secretKey;
    private final UserServiceClient userServiceClient;

    public JwtAuthManager(@Value("${jwt.secret}") String secret, UserServiceClient userServiceClient) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {
            String token = authentication.getCredentials().toString();
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // **Sprawdzamy, czy token wygasł**
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return Mono.empty(); // **Token wygasł, odrzucamy**
            }

            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            // **Sprawdzamy, czy użytkownik istnieje w User-Service przez Feign**
            if (!Boolean.TRUE.equals(userServiceClient.doesUserExist(username))) {
                return Mono.empty(); // **Użytkownik nie istnieje, odrzucamy token**
            }

            List<GrantedAuthority> authorities = role != null ?
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)) : Collections.emptyList();

            return Mono.just(new JwtAuthenticationToken(claims.getSubject(), authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}