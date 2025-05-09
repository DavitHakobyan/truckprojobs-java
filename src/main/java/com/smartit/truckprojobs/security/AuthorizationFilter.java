package com.smartit.truckprojobs.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final FirebaseAuth firebaseAuth;
    private final List<String> allowedPaths;

    public AuthorizationFilter(FirebaseAuth firebaseAuth, String[] allowedPaths) {
        this.firebaseAuth = firebaseAuth;
        this.allowedPaths = List.of(allowedPaths);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (allowedPaths.stream().anyMatch(request.getRequestURI()::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader == null || authHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header is missing or invalid");
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String tokenString = authHeader.substring(7); // Remove "Bearer " prefix
        log.info("Token: {}", tokenString);

        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(tokenString);

            // Attach user ID to the request attribute
            request.setAttribute("uid", decodedToken.getUid());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                    new UsernamePasswordAuthenticationToken(decodedToken.getUid(), null);
                    new UsernamePasswordAuthenticationToken(tokenString, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }

        // Proceed with the next filter or handler
        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        return true;
    }
}
