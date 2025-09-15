package com.juliomesquita.demoAuditoria.infra.configs.security;

import com.juliomesquita.demoAuditoria.infra.configs.security.cors.CorsConfig;
import com.juliomesquita.demoAuditoria.infra.configs.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfig corsConfig;
    private final LogoutHandler logoutHandler;

    public SecurityConfig(
        final JwtAuthenticationFilter jwtAuthenticationFilter, final AuthenticationProvider authenticationProvider,
        final CorsConfig corsConfig, final LogoutHandler logoutHandler
    ) {
        this.jwtAuthenticationFilter = Objects.requireNonNull(jwtAuthenticationFilter);
        this.authenticationProvider = Objects.requireNonNull(authenticationProvider);
        this.corsConfig = Objects.requireNonNull(corsConfig);
        this.logoutHandler = Objects.requireNonNull(logoutHandler);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(c ->c.configurationSource(this.corsConfig.corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**","/api/v1/test/users", "/api/v1/profile/**").permitAll()
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(this.authenticationProvider)
            .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(l -> l
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(this.logoutHandler)
                .logoutSuccessHandler(
                    (request, response, authentication) -> SecurityContextHolder.clearContext()
                )
            )
            .formLogin(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }
}
