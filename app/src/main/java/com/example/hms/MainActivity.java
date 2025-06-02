package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextInputEditText usrn, mail, pass;
    Button signin;
    String username, email, password;
    SAG sag;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);


            
            signin = findViewById(R.id.signin);
            usrn = findViewById(R.id.nm);
            mail = findViewById(R.id.email);
            pass = findViewById(R.id.pass);



            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = Objects.requireNonNull(usrn.getText()).toString().trim();
                    email = Objects.requireNonNull(mail.getText()).toString().trim();
                    password = Objects.requireNonNull(pass.getText()).toString().trim();

                    // Validate inputs
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill all fields", LENGTH_SHORT).show();
                        return;
                    }

                    // Validate email format
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getApplicationContext(), "Invalid email format", LENGTH_SHORT).show();
                        return;
                    }

                    sag = new SAG(username, email, password);
                    usrn.setText("");
                    mail.setText("");
                    pass.setText("");
                    checkFirebaseData();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Initialization error: " + e.getMessage(), LENGTH_SHORT).show();

        }
    }

    private void checkFirebaseData() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("success");

            // Query by email for efficiency
            Query emailQuery = myRef.orderByChild("email").equalTo(email);
            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        boolean isFound = false;

                        // Check for matching username and password
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            try {
                                // Manual deserialization to handle mismatches
                                String storedUsername = snapshot.child("username").getValue(String.class);
                                String storedEmail = snapshot.child("email").getValue(String.class);
                                String storedPassword = snapshot.child("password").getValue(String.class);

                                if (storedUsername != null && storedEmail != null && storedPassword != null &&
                                        storedUsername.equals(username) &&
                                        storedEmail.equals(email) &&
                                        storedPassword.equals(password)) {
                                    isFound = true;
                                    break;
                                }
                            } catch (Exception e) {
                                System.err.println("Error processing snapshot: " + e.getMessage());

                            }
                        }

                        // Display result and navigate on success
                        if (isFound) {
                            Toast.makeText(getApplicationContext(), "Login Successful", LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed: Invalid credentials", LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Data processing error: " + e.getMessage(), LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Database error: " + databaseError.getMessage(), LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Firebase error: " + e.getMessage(), LENGTH_SHORT).show();

        }
    }

    public void login(View v) {
        Intent intent = new Intent(getApplicationContext(), Registering.class);
        startActivity(intent);
    }
}