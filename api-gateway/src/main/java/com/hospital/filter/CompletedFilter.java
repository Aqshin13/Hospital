package com.hospital.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CompletedFilter implements GatewayFilter {

    private final boolean completed;

    public  CompletedFilter(boolean completed){
        this.completed=completed;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String isCompleted = exchange.getRequest()
                .getHeaders()
                .getFirst("is-completed");

        if (this.completed!=Boolean.parseBoolean(isCompleted)) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);

    }
}
