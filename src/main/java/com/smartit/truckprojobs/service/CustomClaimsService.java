package com.smartit.truckprojobs.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.smartit.truckprojobs.model.CustomClaimsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CustomClaimsService {

    private final FirebaseAuth firebaseAuth;
    private final Firestore firestore;

    public CustomClaimsService(FirebaseAuth firebaseAuth, Firestore firestore) {
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    public String addCustomClaims(String uid, CustomClaimsRequest request) throws Exception {
        // Ensure user exists
        UserRecord user;
        try {
            user = firebaseAuth.getUser(uid);
        } catch (FirebaseAuthException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        }

        // Check if user already has custom claims
        if (user.getCustomClaims() != null && !user.getCustomClaims().isEmpty()) {
            throw new Exception("Custom claims already exist for this user");
        }

        // Assign custom claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", request.getRole());

        try {
            firebaseAuth.setCustomUserClaims(uid, claims);
        } catch (FirebaseAuthException e) {
            throw new Exception("Failed to set custom claims", e);
        }

        // Store user data in Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", request.getEmail());
        userData.put("displayName", request.getDisplayName());
        userData.put("photoURL", request.getPhotoURL());
        userData.put("createdOn", com.google.cloud.Timestamp.now());
        userData.put("updatedOn", com.google.cloud.Timestamp.now());

        String collection;
        if ("candidate".equalsIgnoreCase(request.getRole())) {
            collection = "candidates";
        } else if ("employer".equalsIgnoreCase(request.getRole())) {
            collection = "employers";
        } else {
            throw new Exception("Invalid role");
        }

        ApiFuture<WriteResult> writeResult = firestore.collection(collection).document(uid).set(userData, SetOptions.merge());
        writeResult.get(); // Wait for the operation to complete

        return "User updated successfully";
    }
}