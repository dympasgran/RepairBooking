package com.example.repairbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    public interface OnAppointmentClickListener {
        void onClick(Appointment appointment);
    }

    private final ArrayList<Appointment> appointmentList;
    private final OnAppointmentClickListener listener;

    public AppointmentAdapter(ArrayList<Appointment> appointmentList, OnAppointmentClickListener listener) {
        this.appointmentList = appointmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appt = appointmentList.get(position);
        holder.textView.setText("ID: " + appt.getAppointmentId() + "\nStatus: " + appt.getStatus());
        holder.itemView.setOnClickListener(v -> listener.onClick(appt));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textViewAppointment);
        }
    }
}
