package com.example.repairbooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private TextView appointmentInfoText;
    private EditText statusUpdateField;
    private Button updateStatusButton, backButton;
    private FirebaseFirestore db;
    private String appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        appointmentInfoText = findViewById(R.id.appointmentInfoText);
        statusUpdateField = findViewById(R.id.statusUpdateField);
        updateStatusButton = findViewById(R.id.updateStatusButton);
        backButton = findViewById(R.id.backButton);
        db = FirebaseFirestore.getInstance();

        appointmentId = getIntent().getStringExtra("appointmentId");
        loadAppointmentDetails();

        updateStatusButton.setOnClickListener(v -> updateStatus());
        backButton.setOnClickListener(v -> finish());
    }

    private void loadAppointmentDetails() {
        db.collection("appointments").document(appointmentId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Appointment appt = doc.toObject(Appointment.class);
                        String info = "Appointment ID: " + appt.getAppointmentId() +
                                "\nDate: " + appt.getDate() +
                                "\nTime: " + appt.getTime() +
                                "\nDescription: " + appt.getDescription() +
                                "\nStatus: " + appt.getStatus();
                        appointmentInfoText.setText(info);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading details", Toast.LENGTH_SHORT).show());
    }

    private void updateStatus() {
        String newStatus = statusUpdateField.getText().toString().trim();
        if (newStatus.isEmpty()) {
            Toast.makeText(this, "Status can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference ref = db.collection("appointments").document(appointmentId);
        ref.update("status", newStatus)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
    }
}
