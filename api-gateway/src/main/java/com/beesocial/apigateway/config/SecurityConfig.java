package com.beesocial.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                // Disable CSRF for stateless JWT token usage
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // Apply the CORS Configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeExchange(exchange -> exchange
                        .anyExchange().authenticated())
                // Enable JWT authentication for OAuth2 resource server
                .oauth2ResourceServer(oauth2 -> oauth2
                        // Use custom JwtDecoder Bean
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())))
                .build();
    }

    // Configure CORS settings
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:5173"));  // Frontend origin
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(false);  // If credentials like cookies are needed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // Apply CORS for all routes

        return source;
    }

    // Configure JWT decoder to use the shared secret key
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] secretKeyBytes = JWT_SECRET.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec).build();
    }
}

