package com.smartit.truckprojobs.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Set environment variables for Firebase emulator
        System.setProperty("FIRESTORE_EMULATOR_HOST", "localhost:8080");
        System.setProperty("FIREBASE_AUTH_EMULATOR_HOST", "localhost:9099");
        System.setProperty("FIREBASE_DATABASE_EMULATOR_HOST", "localhost:9000");
        System.setProperty("GCLOUD_PROJECT", "truck-pro-jobs");

        /*
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setProjectId("truck-pro-jobs")
                .setDatabaseUrl("http://localhost:9000?ns=truck-pro-jobs")
                // .setDatabaseUrl("https://truck-pro-jobs.firebaseio.com")
                .build();

        return FirebaseApp.initializeApp(options); */

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setProjectId("truck-pro-jobs")
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }
}