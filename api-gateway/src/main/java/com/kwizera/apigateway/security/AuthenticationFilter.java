package com.kwizera.apigateway.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthenticationFilter implements GatewayFilterFactory<Object> {

    private final String jwtSecret;
    private static final Map<String, List<String>> roleAccessMap = Map.of(
            "/restaurant", List.of("OWNER", "CUSTOMER"),
            "/order", List.of("CUSTOMER", "OWNER"),
            "/admin", List.of("ADMIN")
    );

    public AuthenticationFilter() {
        jwtSecret = System.getenv("JWT_SECRET");
    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // ignore /auth endpoints
            if (path.contains("/auth")) {
                return chain.filter(exchange);
            }

            // Check for Authorization header
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            String token = request.getHeaders()
                    .getOrEmpty(HttpHeaders.AUTHORIZATION)
                    .get(0)
                    .replace("Bearer", "");

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8))
                        )
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject();
                String role = claims.get("role", String.class);

                // Check role permission based on path
                Optional<String> matchingKey = roleAccessMap.keySet().stream()
                        .filter(path::startsWith)
                        .findFirst();

                if (matchingKey.isPresent()) {
                    List<String> allowedRoles = roleAccessMap.get(matchingKey.get());
                    if (!allowedRoles.contains(role)) {
                        return onError(exchange, "Forbidden: Role '" + role + "' not allowed", HttpStatus.FORBIDDEN);
                    }
                }

                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Role", role)
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (Exception e) {
                return onError(exchange, "Invalid Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
            }

        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String msg, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
