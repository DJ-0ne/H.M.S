package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Add extends AppCompatActivity {
    TextInputEditText pname,pmail,pno,pstats;
    Button add;
    String patient_name,patient_email,phone_number,patient_status,gender;
    AGAS agas;
    RadioGroup rgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add = findViewById(R.id.add);
        pname = findViewById(R.id.user);
        pmail = findViewById(R.id.mailz);
        pno = findViewById(R.id.phone);
        pstats = findViewById(R.id.status);
        rgender = findViewById(R.id.rgender);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient_name = Objects.requireNonNull(pname.getText()).toString();
                pname.setText("");
                patient_email = Objects.requireNonNull(pmail.getText()).toString();
                pmail.setText("");
                phone_number = Objects.requireNonNull(pno.getText()).toString();
                pno.setText("");
                patient_status = Objects.requireNonNull(pstats.getText()).toString();
                pstats.setText("");
                if (patient_name.isEmpty() || patient_email.isEmpty() || phone_number.isEmpty() || patient_status.isEmpty()) {
                    Toast.makeText(Add.this, "Please fill all fields", LENGTH_SHORT).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(patient_email).matches()) {
                    Toast.makeText(Add.this, "Invalid email format", LENGTH_SHORT).show();
                    return;
                }
                if (!phone_number.matches("^[0-9+]{9,15}$")) {
                    Toast.makeText(Add.this, "Invalid phone number format", LENGTH_SHORT).show();
                    return;
                }


                int selectedId = rgender.getCheckedRadioButtonId();

                if (selectedId == R.id.m) {
                    gender = "Male";
                } else if (selectedId == R.id.f) {
                    gender = "Female";
                } else {
                    Toast.makeText(Add.this, "Please select a gender", LENGTH_SHORT).show();
                    return;
                }

                agas = new AGAS(patient_name,patient_email,phone_number,patient_status,gender);
                addpatient();
            }
        });

    }

    private void addpatient() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("beautiful");

        Ref.push().setValue(agas);
        Toast.makeText(getApplicationContext(),"Submitted successfully",LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
    }
}