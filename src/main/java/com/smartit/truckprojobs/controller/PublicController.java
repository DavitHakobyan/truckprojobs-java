package com.smartit.truckprojobs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping
    public String getPublicData() {
        return "This is public data";
    }

    @GetMapping(path = "/api/public")
    public ResponseEntity<Map<String, Object>> getPublicInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the public API");
        response.put("status", "active");
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }
}