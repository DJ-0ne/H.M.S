package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        usrn = findViewById(R.id.usr);
        mail= findViewById(R.id.mail);
        phone = findViewById(R.id.pno);
        pass = findViewById(R.id.pass);
        con = findViewById(R.id.con);
        radio = findViewById(R.id.gender);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usrn.getText().toString();
                usrn.setText("");
                email = mail.getText().toString();
                mail.setText("");
                phonenumber = phone.getText().toString();
                phone.setText("");
                password = pass.getText().toString();
                pass.setText("");
                confirm_password = con.getText().toString();
                con.setText("");

                int selectedId = radio.getCheckedRadioButtonId();

                if (selectedId == R.id.m) {
                    gender = "Male";
                } else if (selectedId == R.id.f) {
                    gender = "Female";
                } else {
                    gender = "null";
                }



                Toast.makeText(Registering.this, "Registered succesfully", LENGTH_SHORT).show();
                gas = new GAS(username,email,phonenumber,password,gender,confirm_password);
                connfirebase();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void connfirebase() {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("success");

        // Read from the database
        myRef.push().setValue(gas);

    }

    public void Signin(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}