package com.example.hms;

public class GAS {
    private String username,email,phonenumber,password,gender,confirm_password;

    public GAS(String username, String email, String phonenumber, String password, String gender, String confirm_password) {
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.gender = gender;
        this.confirm_password = confirm_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
