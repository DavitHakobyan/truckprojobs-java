package com.smartit.truckprojobs.config;

import com.google.firebase.auth.FirebaseAuth;
import com.smartit.truckprojobs.security.AuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] ALLOWED_PATHS = {
            "/",
            "/**",
            "/actuator/**",
            "/static/**",
            "/index.html",
            "/favicon.ico",
            "/api/public",
            "/api/jobposts",
            "/api/jobposts"
    };

    private final FirebaseAuth firebaseAuth;

    public SecurityConfig(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(ALLOWED_PATHS)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(ALLOWED_PATHS).permitAll()
                        .requestMatchers("/api/secure").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthorizationFilter(firebaseAuth, ALLOWED_PATHS), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}