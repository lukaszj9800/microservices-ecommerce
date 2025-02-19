package pl.jagiela.microservices_ecommerce.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthManager jwtAuthManager;

    public SecurityConfig(JwtAuthManager jwtAuthManager) {
        this.jwtAuthManager = jwtAuthManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain() {
        return ServerHttpSecurity.http()
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/auth/register").permitAll()
                        .pathMatchers("/api/v1/auth/login").permitAll()
                        .pathMatchers("/api/v1/auth/exist").permitAll()
                        .pathMatchers("/api/v1/users/**").hasRole("USER")
                        .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .authenticationManager(jwtAuthManager)
                .build();
    }
}
