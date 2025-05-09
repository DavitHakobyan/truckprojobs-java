package com.smartit.truckprojobs.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FakeIdGenerator {

    public static void main(String[] args) {
        String subject = "candidate"; // Change to "employer" as needed
        try {
            String token = generateFakeIdToken(subject);
            System.out.println("Fake ID Token: " + token);
        } catch (Exception e) {
            log.error("An error occurred while generating the fake ID token", e);
        }
    }

    public static String generateFakeIdToken(String subject) {
        // Define the claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "https://securetoken.google.com/my-project-id");
        claims.put("aud", "my-project-id");
        claims.put("sub", "fake-" + subject + "-id");
        claims.put("email", subject + "@example.com");
        claims.put("email_verified", true);
        claims.put("iat", new Date().getTime() / 1000); // Issued at (in seconds)
        claims.put("exp", (new Date().getTime() + 3600000) / 1000); // Expiration (1 hour from now, in seconds)
        claims.put("auth_time", new Date().getTime() / 1000); // Authentication time (in seconds)
        claims.put("user_id", "fake-" + subject + "-id");

        // Generate a secure key for HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Generate the token using the secure key
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }
}