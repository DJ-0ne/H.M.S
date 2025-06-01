package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registering extends AppCompatActivity {
    TextInputEditText usrn,mail,phone,pass,con;
    Button register;
    String username,email,phonenumber,password,gender,confirm_password;
    RadioGroup radio;
    GAS gas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);

        register = findViewById(R.id.register);
        usrn = findViewById(R.id.nm);
        mail= findViewById(R.id.mailz);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        con = findViewById(R.id.con);
        radio = findViewById(R.id.gender);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = Objects.requireNonNull(usrn.getText()).toString();
                usrn.setText("");
                email = Objects.requireNonNull(mail.getText()).toString();
                mail.setText("");
                phonenumber = Objects.requireNonNull(phone.getText()).toString();
                phone.setText("");
                password = Objects.requireNonNull(pass.getText()).toString();
                pass.setText("");
                confirm_password = Objects.requireNonNull(con.getText()).toString();
                con.setText("");

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                    Toast.makeText(Registering.this, "Please fill all fields", LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirm_password)) {
                    Toast.makeText(Registering.this, "Passwords do not match", LENGTH_SHORT).show();
                    return;
                }
                if (!phonenumber.matches("^[0-9+]{9,15}$")) {
                    Toast.makeText(Registering.this, "Invalid phone number format", LENGTH_SHORT).show();
                    return;
                }

                // Validate email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Registering.this, "Invalid email format", LENGTH_SHORT).show();
                    return;
                }

                int selectedId = radio.getCheckedRadioButtonId();

                if (selectedId == R.id.m) {
                    gender = "Male";
                } else if (selectedId == R.id.f) {
                    gender = "Female";
                } else {
                    Toast.makeText(Registering.this, "Please select a gender", LENGTH_SHORT).show();
                    return;
                }


                Validation();

            }
        });

    }

    private void Validation() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("success");
        // Check username
        Ref.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot usernameSnapshot) {
                if (usernameSnapshot.exists()) {
                    Toast.makeText(Registering.this, "Username already exists", LENGTH_SHORT).show();
                } else {
                    // Check email
                    Ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot emailSnapshot) {
                            if (emailSnapshot.exists()) {
                                Toast.makeText(Registering.this, "Email already exists", LENGTH_SHORT).show();
                            } else {
                                // Check phone number
                                Ref.orderByChild("phonenumber").equalTo(phonenumber).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot phoneSnapshot) {
                                        if (phoneSnapshot.exists()) {
                                            Toast.makeText(Registering.this, "Phone number already exists", LENGTH_SHORT).show();
                                        } else {
                                            // All fields are unique, proceed with registration
                                            gas = new GAS(username, email, phonenumber, password, gender, confirm_password);
                                            Ref.push().setValue(gas).addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Registering.this, "Registered successfully", LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(Registering.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Registering.this, "Database error: " + error.getMessage(), LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Registering.this, "Database error: " + error.getMessage(), LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Registering.this, "Database error: " + error.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    public void Signin(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}