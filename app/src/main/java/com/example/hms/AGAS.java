package com.example.hms;

public class AGAS {
    private String patient_name;
    private String patient_email;
    private String phone_number;
    private String patient_status;
    private String gender;

    // Default constructor required for Firebase
    public AGAS() {}

    public AGAS(String patient_name, String patient_email, String phone_number, String patient_status, String gender) {
        this.patient_name = patient_name;
        this.patient_email = patient_email;
        this.phone_number = phone_number;
        this.patient_status = patient_status;
        this.gender = gender;
    }

    // Getters
    public String getPatient_name() {
        return patient_name;
    }

    public String getPatient_status() {
        return patient_status;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getGender() {
        return gender;
    }

    // Setters (optional, depending on your needs)
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPatient_status(String patient_status) {
        this.patient_status = patient_status;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}