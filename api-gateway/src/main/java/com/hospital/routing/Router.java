package com.hospital.routing;


import com.hospital.filter.RoleFilter;
import com.hospital.filter.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Router {


    private final SecurityFilter securityFilter;



    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                .route(p -> p.path("/api/v1/users/doctor/**")
                        .filters(f->f.filter(securityFilter)
                                .filter(new RoleFilter(List.of("DOCTOR"))))
                        .uri("lb://user-service"))
                .route(p -> p.path("/api/v1/users/patient/**")
                        .filters(f->f.filter(securityFilter)
                                .filter(new RoleFilter(List.of("PATIENT"))))
                        .uri("lb://user-service"))
                .build();
    }



}
