package pl.jagiela.microservices_ecommerce.api_gateway.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    private static final String SECRET_KEY = "MySuperSecretKeyForJWTMySuperSecretKeyForJWT"; // Musi mieć minimum 256 bitów

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
