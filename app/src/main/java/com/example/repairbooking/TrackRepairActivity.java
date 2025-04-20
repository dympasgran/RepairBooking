package com.example.repairbooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrackRepairActivity extends AppCompatActivity {

    private TextView statusTextView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_repair);

        statusTextView = findViewById(R.id.statusTextView);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getCurrentUser().getUid();

        db.collection("appointments")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String status = queryDocumentSnapshots.getDocuments().get(0).getString("status");
                        statusTextView.setText("Current Status: " + status);
                    } else {
                        statusTextView.setText("No appointments found.");
                    }
                })
                .addOnFailureListener(e -> statusTextView.setText("Error: " + e.getMessage()));
    }
}
