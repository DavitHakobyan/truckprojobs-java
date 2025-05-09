//package com.smartit.truckprojobs.controller;
//
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.auth.FirebaseAuth;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/test-emulator")
//public class EmulatorTestController {
//
//    private final Firestore firestore;
//    private final FirebaseAuth firebaseAuth;
//
//    public EmulatorTestController(Firestore firestore, FirebaseAuth firebaseAuth) {
//        this.firestore = firestore;
//        this.firebaseAuth = firebaseAuth;
//    }
//
//    @GetMapping("/firestore")
//    public ResponseEntity<?> testFirestore() {
//        try {
//            firestore.collection("test").document("testDoc").set(Map.of("key", "value")).get();
//            return ResponseEntity.ok("Connected to Firestore emulator");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Firestore emulator connection failed: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/auth")
//    public ResponseEntity<?> testAuth() {
//        try {
//            firebaseAuth.getUser("testUid"); // Replace with a valid UID in the emulator
//            return ResponseEntity.ok("Connected to Firebase Auth emulator");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth emulator connection failed: " + e.getMessage());
//        }
//    }
//}