package com.example.repairbooking;

public class Appointment {
    private String appointmentId;
    private String userId;
    private String firstName; // Add first name
    private String lastName;  // Add last name
    private String telephone; // Add telephone number
    private String date;
    private String time;
    private String description;
    private String status;

    public Appointment() {} // Needed for Firestore

    // Getters and setters for all fields
    public String getAppointmentId() { return appointmentId; }
    public String getUserId() { return userId; }
    public String getFirstName() { return firstName; } // Getter for first name
    public String getLastName() { return lastName; } // Getter for last name
    public String getTelephone() { return telephone; } // Getter for telephone number
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }

    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setFirstName(String firstName) { this.firstName = firstName; } // Setter for first name
    public void setLastName(String lastName) { this.lastName = lastName; } // Setter for last name
    public void setTelephone(String telephone) { this.telephone = telephone; } // Setter for telephone number
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
}
