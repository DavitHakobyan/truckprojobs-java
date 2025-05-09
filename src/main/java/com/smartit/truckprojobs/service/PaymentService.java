package com.smartit.truckprojobs.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentService() {
        // Default constructor
    }

    public String createCheckoutSession(String uid, Map<String, Object> requestData) throws StripeException {
        // Create a Stripe Checkout Session
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", new String[]{"card"});
        params.put("mode", "payment");
        params.put("success_url", requestData.get("successUrl"));
        params.put("cancel_url", requestData.get("cancelUrl"));

        // Add line items
        params.put("line_items", requestData.get("lineItems"));

        Session session = Session.create(params);
        return session.getUrl();
    }

    public void handleWebhook(HttpServletRequest request) throws IOException {
        String payload = getRequestBody(request);
        String sigHeader = request.getHeader("Stripe-Signature");

        try {
            // Verify the webhook signature
            Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);

            // Process the event (e.g., payment succeeded, etc.)
            // Add your custom logic here
        } catch (Exception e) {
            throw new RuntimeException("Webhook verification failed", e);
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}