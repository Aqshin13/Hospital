package com.hospital.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class RoleFilter implements GatewayFilter {

    private final List<String> requiredRoles;

    public RoleFilter(List<String> requiredRoles) {
        this.requiredRoles = requiredRoles;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userRole = exchange.getRequest()
                .getHeaders()
                .getFirst("role");

        if (userRole == null || !requiredRoles.contains(userRole)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete(); // 403 qaytarır
        }

        return chain.filter(exchange);
    }
}
