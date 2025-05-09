package com.smartit.truckprojobs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSecureInfo(HttpServletRequest request) {
        // Get the user ID that was set by the AuthFilter
        String userId = (String) request.getAttribute("uid");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is protected data from the secure API");
        response.put("status", "authenticated");
        response.put("userId", userId);
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }
}