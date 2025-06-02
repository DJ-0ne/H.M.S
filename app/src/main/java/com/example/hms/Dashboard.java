package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dashboard extends AppCompatActivity {
    Button button,dis;
    LinearLayout patientsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dashboard);
        } catch (Exception e) {
            Toast.makeText(this, "Error loading layout: " + e.getMessage(), LENGTH_SHORT).show();
            return;
        }

        // Initialize views with null check
        button = findViewById(R.id.button);
        patientsContainer = findViewById(R.id.card);

        if (patientsContainer == null || button == null) {
            Toast.makeText(this, "Error: Required views not found", LENGTH_SHORT).show();
            return;
        }

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            Toast.makeText(this, "Firebase initialization failed: " + e.getMessage(), LENGTH_SHORT).show();
            return;
        }

        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Add.class);
            startActivity(intent);
        });

        // Initialize Firebase Database
        FirebaseDatabase database;
        DatabaseReference ref;
        try {
            database = FirebaseDatabase.getInstance();
            ref = database.getReference("total");
        } catch (Exception e) {
            Toast.makeText(this, "Database access failed: " + e.getMessage(), LENGTH_SHORT).show();
            return;
        }

        // Listen for database changes
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    patientsContainer.removeAllViews(); // Clear only patient CardViews

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AGAS patient = snapshot.getValue(AGAS.class);
                        if (patient != null && patient.getPatient_name() != null && patient.getPatient_status() != null) {
                            // Create new CardView programmatically
                            CardView cardView = new CardView(Dashboard.this);
                            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    (int) (80 * getResources().getDisplayMetrics().density)
                            );
                            cardParams.setMargins(
                                    (int) (16 * getResources().getDisplayMetrics().density),
                                    (int) (8 * getResources().getDisplayMetrics().density),
                                    (int) (16 * getResources().getDisplayMetrics().density),
                                    (int) (8 * getResources().getDisplayMetrics().density)
                            );
                            cardView.setLayoutParams(cardParams);
                            cardView.setCardElevation(8);
                            cardView.setRadius(16); // Match activity_add.xml
                            try {
                                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                            } catch (Exception e) {
                                cardView.setCardBackgroundColor(0xFFFFFFFF);
                            }


                            LinearLayout innerLayout = new LinearLayout(Dashboard.this);
                            innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));
                            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
                            innerLayout.setPadding(
                                    (int) (16 * getResources().getDisplayMetrics().density),
                                    (int) (16 * getResources().getDisplayMetrics().density),
                                    (int) (16 * getResources().getDisplayMetrics().density),
                                    (int) (16 * getResources().getDisplayMetrics().density)
                            );

                            // Create ImageView
                            ImageView imageView = new ImageView(Dashboard.this);
                            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                                    (int) (40 * getResources().getDisplayMetrics().density),
                                    (int) (40 * getResources().getDisplayMetrics().density)
                            );
                            imageParams.setMargins(0, 0, (int) (16 * getResources().getDisplayMetrics().density), 0);
                            imageView.setLayoutParams(imageParams);
                            try {
                                imageView.setImageResource(R.drawable.patient);
                            } catch (Exception e) {
                                Toast.makeText(Dashboard.this, "Image resource not found", LENGTH_SHORT).show();
                            }

                            // Create Name TextView
                            TextView nameTextView = new TextView(Dashboard.this);
                            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                                    0,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    1f
                            );
                            nameTextView.setLayoutParams(nameParams);
                            nameTextView.setText(patient.getPatient_name());
                            nameTextView.setTextSize(18);
                            nameTextView.setTextColor(getResources().getColor(R.color.black));
                            nameTextView.setTypeface(Typeface.DEFAULT_BOLD); // Fix for setTextStyle

                            // Create Status TextView
                            TextView statusTextView = new TextView(Dashboard.this);
                            LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            statusTextView.setLayoutParams(statusParams);
                            statusTextView.setText(patient.getPatient_status());
                            statusTextView.setTextSize(16);
                            statusTextView.setTextColor(getResources().getColor(R.color.black));

                            // Add views to layouts
                            innerLayout.addView(imageView);
                            innerLayout.addView(nameTextView);
                            innerLayout.addView(statusTextView);
                            cardView.addView(innerLayout);

                            // Add CardView to container
                            patientsContainer.addView(cardView);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(Dashboard.this, "Error loading patients: " + e.getMessage(), LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Dashboard.this, "Failed to load data: " + databaseError.getMessage(), LENGTH_SHORT).show();
            }
        });
    }
}