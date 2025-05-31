package com.example.hms;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextInputEditText usrn,mail,pass;
    Button signin;
    String username,email,password;
    SAG sag;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin = findViewById(R.id.signin);
        usrn = findViewById(R.id.usr);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.pass);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usrn.getText().toString();
                usrn.setText("");
                email = mail.getText().toString();
                mail.setText("");
                password = pass.getText().toString();
                pass.setText("");


                sag = new SAG(username,email,password);

                connfirebase();
            }
        });
    }

    private void connfirebase() {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("success");

        // Read from the database
        myRef.push().setValue(sag);
    }

    public void login(View v){
        Intent intent = new Intent(getApplicationContext(), Registering.class);
        startActivity(intent);
    }

}