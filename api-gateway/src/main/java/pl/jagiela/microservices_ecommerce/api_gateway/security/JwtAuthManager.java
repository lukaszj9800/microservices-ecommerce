package pl.jagiela.microservices_ecommerce.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthManager implements ReactiveAuthenticationManager {
    private final SecretKey secretKey;

    public JwtAuthManager(SecretKey secretKey) {
        this.secretKey = secretKey;
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

            String role = claims.get("role", String.class);
            List<GrantedAuthority> authorities = role != null ?
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)) : Collections.emptyList();

            return Mono.just(new JwtAuthenticationToken(claims.getSubject(), authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}