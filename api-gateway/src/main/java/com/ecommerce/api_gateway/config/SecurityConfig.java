package com.ecommerce.api_gateway.config;

import com.ecommerce.api_gateway.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        LOG.debug("Configuring SecurityWebFilterChain");

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**", "/error").permitAll()
                        .pathMatchers(HttpMethod.GET, "/product/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/product-detail/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/product-detail/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/inventory/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/inventory/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/orders/**").hasRole("USER")
                        .pathMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/orders/**").hasRole("USER")
                        .pathMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("USER", "ADMIN")
                        .anyExchange().authenticated()
                )
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        return http.build();
    }
}