package com.hospital.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Component
public class SecurityFilter implements GatewayFilter {

    SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        String token = extractJwtFromRequest(exchange
//                .getRequest()
//                .getHeaders()
//                .getFirst("Authorization"));
//
//        if (token==null || token.isEmpty()) {
//            return reject(exchange, HttpStatus.UNAUTHORIZED);
//        }
//        User user=null;
//        try{
//             user = verifyToken(token);
//        } catch (ExpiredJwtException e) {
//            return rejectWithBody(exchange, HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED");
//        } catch (JwtException e) {
//            return rejectWithBody(exchange, HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
//        }
//
//        if(!user.isActive()){
//            return reject(exchange, HttpStatus.UNAUTHORIZED);
//        }
//
//        ServerHttpRequest mutatedRequest = exchange.getRequest()
//                .mutate()
//                .header("user-id", user.id().toString())
//                .header("role", user.role())
//                .build();
//
//        ServerWebExchange mutatedExchange = exchange
//                .mutate()
//                .request(mutatedRequest)
//                .build();
//        return chain.filter(mutatedExchange);
//    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
                .map(this::extractJwtFromRequest)
                .filter(token -> !token.isEmpty())
                .switchIfEmpty(reject(exchange, HttpStatus.UNAUTHORIZED).then(Mono.empty()))
                .flatMap(token -> verifyTokenReactive(token, exchange))
                .filter(User::isActive)
                .switchIfEmpty(reject(exchange, HttpStatus.UNAUTHORIZED).then(Mono.empty()))
                .flatMap(user -> chain.filter(mutateExchange(exchange, user)));
    }


    private Mono<User> verifyTokenReactive(String token, ServerWebExchange exchange) {
        return Mono.fromCallable(() -> verifyToken(token))
                .onErrorResume(ExpiredJwtException.class,
                        e -> rejectWithBody(exchange,
                                HttpStatus.UNAUTHORIZED,
                                "TOKEN_EXPIRED").then(Mono.empty()))
                .onErrorResume(JwtException.class,
                        e -> rejectWithBody(exchange,
                                HttpStatus.UNAUTHORIZED,
                                "INVALID_TOKEN").then(Mono.empty()));
    }

    private ServerWebExchange mutateExchange(ServerWebExchange exchange, User user) {
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("user-id", user.id().toString())
                .header("role", user.role())
                .build();
        return exchange.mutate().request(mutatedRequest).build();
    }

    private String extractJwtFromRequest(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> reject(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> rejectWithBody(ServerWebExchange exchange,
                                      HttpStatus status,
                                      String errorCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {"status": %d, "code": "%s"}
        """.formatted(status.value(), errorCode);

        DataBuffer buffer = response.bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    public User verifyToken(String token) {
        ObjectMapper mapper = new ObjectMapper();
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> claims = parser.parseClaimsJws(token);
        var subject = claims.getBody().getSubject();
        var tokenSubject = mapper.readValue(subject, TokenSubject.class);
        return new User(tokenSubject.id(), tokenSubject.active(), tokenSubject.role());

    }

    public record TokenSubject(UUID id, boolean active, String role) {
    }

    public record User(UUID id, boolean isActive, String role) {
    }


}
