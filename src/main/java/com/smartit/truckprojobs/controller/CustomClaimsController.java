package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.CustomClaimsRequest;
import com.smartit.truckprojobs.service.CustomClaimsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/add-custom-claims")
public class CustomClaimsController {

    private final CustomClaimsService customClaimsService;

    public CustomClaimsController(CustomClaimsService customClaimsService) {
        this.customClaimsService = customClaimsService;
    }

    //    @PostMapping("/add-custom-claims")
    @PostMapping
    public ResponseEntity<?> addCustomClaims(@RequestBody CustomClaimsRequest customClaimsRequest,
                                             HttpServletRequest request,
                                             Authentication authentication) {

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Extract the authenticated user's UID from the authentication object
            // String uid = authentication.getName();
            // Retrieve the UID from the request attribute
            String uid = (String) request.getAttribute("uid");
            log.info("UID: {}", uid);

            // Call the service to add custom claims
            String response = customClaimsService.addCustomClaims(uid, customClaimsRequest);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}