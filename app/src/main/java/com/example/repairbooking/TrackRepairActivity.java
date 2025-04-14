package com.example.repairbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrackRepairActivity extends AppCompatActivity {

    private TextView appointmentIdTextView, statusTextView, notesTextView;
    private Button logoutButton;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_repair);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Bind views
        appointmentIdTextView = findViewById(R.id.appointmentIdTextView);
        statusTextView = findViewById(R.id.statusTextView);
        notesTextView = findViewById(R.id.notesTextView);
        logoutButton = findViewById(R.id.logoutButton);

        // Get appointment ID from intent
        String appointmentId = getIntent().getStringExtra("appointmentId");

        if (appointmentId != null && !appointmentId.isEmpty()) {
            appointmentIdTextView.setText("Appointment ID: " + appointmentId);
            loadRepairStatus(appointmentId);
        } else {
            Toast.makeText(this, "No appointment ID provided", Toast.LENGTH_SHORT).show();
        }

        // Logout logic
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(TrackRepairActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadRepairStatus(String appointmentId) {
        firestore.collection("appointments").document(appointmentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String status = documentSnapshot.getString("status");
                        String notes = documentSnapshot.getString("technicianNotes");

                        statusTextView.setText("Status: " + (status != null ? status : "Pending"));
                        notesTextView.setText("Notes: " + (notes != null ? notes : "No updates yet."));
                    } else {
                        Toast.makeText(this, "Appointment not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load repair info: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
