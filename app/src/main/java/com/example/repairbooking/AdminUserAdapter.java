package com.example.repairbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairbooking.R;
import com.example.repairbooking.User;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public AdminUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getFirstName() + " " + user.getLastName());
        holder.emailTextView.setText(user.getEmail());
        holder.telephoneTextView.setText("Phone: " + user.getTelephone());
        holder.roleSpinner.setSelection(getRoleIndex(user.getRole()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private int getRoleIndex(String role) {
        if (role == null) return 0;
        switch (role.toLowerCase()) {
            case "admin": return 0;
            case "technician": return 1;
            case "client": return 2;
            default: return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, telephoneTextView;
        Spinner roleSpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            telephoneTextView = itemView.findViewById(R.id.telephoneTextView);
            roleSpinner = itemView.findViewById(R.id.roleSpinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.user_roles, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roleSpinner.setAdapter(adapter);
        }
    }
}
