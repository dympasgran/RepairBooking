package com.example.repairbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private AdminUserAdapter userAdapter;
    private List<User> userList;
    private FirebaseFirestore db;
    private Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        userRecyclerView = findViewById(R.id.userRecyclerView);
        logoutButton = findViewById(R.id.logoutButton);

        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();
        userAdapter = new AdminUserAdapter(this, userList);

        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(userAdapter);

        loadUsers();

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUsers() {
        db.collection("users").get()
                .addOnSuccessListener(querySnapshot -> {
                    userList.clear();
                    for (DocumentSnapshot doc : querySnapshot) {
                        String uid = doc.getId();
                        String email = doc.getString("email");
                        String role = doc.getString("role");
                        String firstName = doc.getString("firstName");
                        String lastName = doc.getString("lastName");
                        String telephone = doc.getString("telephone");

                        userList.add(new User(uid, firstName, lastName, email, telephone, role));
                    }
                    userAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load users: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
