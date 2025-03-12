package com.coders.RetailEdge.services.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class WebFluxConfig {

    @Bean
    public WebFilter jwtFilter(JwtInterceptor jwtInterceptor) {
        return jwtInterceptor;
    }
}