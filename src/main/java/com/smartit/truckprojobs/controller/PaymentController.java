package com.smartit.truckprojobs.controller;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.smartit.truckprojobs.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/secure/stripe")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Map<String, Object> requestData, Authentication authentication) {
        try {
            // Extract the authenticated user's UID
            String uid = authentication.getName();

            // Call the service to create a checkout session
            String sessionUrl = paymentService.createCheckoutSession(uid, requestData);

            return ResponseEntity.ok().body(Map.of("url", sessionUrl));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(HttpServletRequest request) {
        try {
            // Call the service to handle the webhook
            paymentService.handleWebhook(request);

            return ResponseEntity.ok().body("Webhook handled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}