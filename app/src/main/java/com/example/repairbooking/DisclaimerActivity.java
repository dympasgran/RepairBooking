package com.example.repairbooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisclaimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        TextView disclaimerText = findViewById(R.id.disclaimerText);
        Button acceptButton = findViewById(R.id.acceptButton);
        Button declineButton = findViewById(R.id.declineButton);

        disclaimerText.setText("By scheduling an appointment, you agree to our terms and conditions. This includes responsibility for accurate information, service limitations, and potential repair costs.");

        acceptButton.setOnClickListener(v -> {
            setResult(Activity.RESULT_OK);
            finish();
        });

        declineButton.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}
