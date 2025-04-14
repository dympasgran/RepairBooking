package com.example.repairbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ClientDashboardActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText issueDescription;
    private Button attachFilesButton, scheduleAppointmentButton;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize views
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        issueDescription = findViewById(R.id.issueDescription);
        attachFilesButton = findViewById(R.id.attachFilesButton);
        scheduleAppointmentButton = findViewById(R.id.scheduleAppointmentButton);

        scheduleAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect data
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String description = issueDescription.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(ClientDashboardActivity.this, "Please provide a description", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show disclaimer prompt
                showDisclaimerDialog(year, month, day, hour, minute, description);
            }
        });
    }

    private void showDisclaimerDialog(final int year, final int month, final int day, final int hour, final int minute, final String description) {
        // Intent to show disclaimer activity
        Intent intent = new Intent(ClientDashboardActivity.this, DisclaimerActivity.class);
        startActivityForResult(intent, 1);  // Start activity and expect a result (from the Disclaimer)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // If the user agreed to the terms and conditions, confirm the appointment
            scheduleAppointmentConfirmation();
        }
    }

    private void scheduleAppointmentConfirmation() {
        // Generate appointment ID
        String appointmentId = generateAppointmentId();

        // Get current user ID (this will be the client who is scheduling the appointment)
        String userId = auth.getCurrentUser().getUid();

        // Fetch the user's details from Firestore
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the user's name and telephone number
                        String firstName = documentSnapshot.getString("firstName");
                        String lastName = documentSnapshot.getString("lastName");
                        String telephone = documentSnapshot.getString("telephone");

                        // Store the appointment details in Firestore
                        Map<String, Object> appointmentData = new HashMap<>();
                        appointmentData.put("userId", userId);
                        appointmentData.put("appointmentId", appointmentId);
                        appointmentData.put("firstName", firstName);  // Add first name
                        appointmentData.put("lastName", lastName);    // Add last name
                        appointmentData.put("telephone", telephone);  // Add telephone number
                        appointmentData.put("date", datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        appointmentData.put("time", timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        appointmentData.put("description", issueDescription.getText().toString());
                        appointmentData.put("status", "Pending");

                        // Store the appointment document in Firestore under "appointments"
                        firestore.collection("appointments").document(appointmentId)
                                .set(appointmentData)
                                .addOnSuccessListener(aVoid -> {
                                    // Show confirmation message
                                    Toast.makeText(ClientDashboardActivity.this, "Appointment Confirmed! ID: " + appointmentId, Toast.LENGTH_LONG).show();

                                    // Redirect to confirmation page
                                    Intent intent = new Intent(ClientDashboardActivity.this, AppointmentConfirmationActivity.class);
                                    intent.putExtra("appointmentId", appointmentId);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    // Handle error
                                    Toast.makeText(ClientDashboardActivity.this, "Error scheduling appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error fetching user data
                    Toast.makeText(ClientDashboardActivity.this, "Error fetching user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String generateAppointmentId() {
        // You can generate a more robust ID, e.g., using Firestore auto ID or UUID
        return "APPT-" + System.currentTimeMillis();
    }
}
