package com.example.repairbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TechnicianDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private ArrayList<Appointment> appointmentList = new ArrayList<>();
    private FirebaseFirestore db;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_dashboard);

        recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logoutButton = findViewById(R.id.logoutButton);
        db = FirebaseFirestore.getInstance();

        adapter = new AppointmentAdapter(appointmentList, this::onAppointmentClick);
        recyclerView.setAdapter(adapter);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(TechnicianDashboardActivity.this, LoginActivity.class));
            finish();
        });

        loadAppointments();
    }

    private void loadAppointments() {
        db.collection("appointments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    appointmentList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Appointment appt = doc.toObject(Appointment.class);
                        appointmentList.add(appt);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load appointments", Toast.LENGTH_SHORT).show());
    }

    private void onAppointmentClick(Appointment appointment) {
        Intent intent = new Intent(this, AppointmentDetailsActivity.class);
        intent.putExtra("appointmentId", appointment.getAppointmentId());
        startActivity(intent);
    }
}
