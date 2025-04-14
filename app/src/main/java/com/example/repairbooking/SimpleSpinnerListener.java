package com.example.repairbooking;

import android.view.View;
import android.widget.AdapterView;

public abstract class SimpleSpinnerListener implements AdapterView.OnItemSelectedListener {
    private boolean isFirstSelection = true;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (isFirstSelection) {
            isFirstSelection = false;  // Skip initial callback
            return;
        }
        String selected = parent.getItemAtPosition(position).toString();
        onItemSelected(selected);
    }

    public abstract void onItemSelected(String selectedRole);

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
