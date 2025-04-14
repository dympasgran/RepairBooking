package com.example.repairbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AppointmentConfirmationActivity extends AppCompatActivity {

    private TextView appointmentIdTextView;
    private Button backToDashboardButton, trackRepairButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirmation);

        // Bind views
        appointmentIdTextView = findViewById(R.id.appointmentIdTextView);
        backToDashboardButton = findViewById(R.id.backToDashboardButton);
        trackRepairButton = findViewById(R.id.trackRepair);
        logoutButton = findViewById(R.id.logout);

        // Get and show appointment ID
        String appointmentId = getIntent().getStringExtra("appointmentId");
        if (appointmentId != null) {
            appointmentIdTextView.setText("Appointment ID: " + appointmentId);
        }

        // Button: Back to Dashboard
        backToDashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(AppointmentConfirmationActivity.this, ClientDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Button: Track Repair
        trackRepairButton.setOnClickListener(view -> {
            Intent intent = new Intent(AppointmentConfirmationActivity.this, TrackRepairActivity.class);
            startActivity(intent);
        });

        // Button: Logout
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AppointmentConfirmationActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
