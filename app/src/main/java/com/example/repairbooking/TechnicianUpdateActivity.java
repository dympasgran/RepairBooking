package com.example.repairbooking;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TechnicianUpdateActivity extends AppCompatActivity {

    private TextView clientNameTextView, issueTextView;
    private Spinner statusSpinner;
    private Button updateButton, backButton;
    private FirebaseFirestore db;
    private String appointmentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_update);

        db = FirebaseFirestore.getInstance();

        clientNameTextView = findViewById(R.id.clientNameTextView);
        issueTextView = findViewById(R.id.issueTextView);
        statusSpinner = findViewById(R.id.statusSpinner);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        // Populate spinner with statuses
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.repair_statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        // Get appointment details from intent
        appointmentId = getIntent().getStringExtra("appointmentId");
        String clientName = getIntent().getStringExtra("clientName");
        String issue = getIntent().getStringExtra("issue");

        clientNameTextView.setText(clientName);
        issueTextView.setText(issue);

        updateButton.setOnClickListener(v -> {
            String selectedStatus = statusSpinner.getSelectedItem().toString();
            if (appointmentId != null) {
                db.collection("appointments").document(appointmentId)
                        .update("status", selectedStatus)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
