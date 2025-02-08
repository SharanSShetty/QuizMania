package com.example.quizmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up the Toolbar (ActionBar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize buttons
        Button javaButton = findViewById(R.id.btn_java);
        Button pythonButton = findViewById(R.id.btn_python);
        Button cppButton = findViewById(R.id.btn_cpp);
        Button logoutButton = findViewById(R.id.btn_logout);

        // Set button click listeners
        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Java button click
                Intent javaIntent = new Intent(home.this, jlevel.class);
                startActivity(javaIntent);
            }
        });

        pythonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Python button click
                Intent pythonIntent = new Intent(home.this, pylevel.class);
                startActivity(pythonIntent);
            }
        });

        cppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle C++ button click
                Intent cppIntent = new Intent(home.this, clevel.class);
                startActivity(cppIntent);
            }
        });

        // Handle logout button click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a toast message for logout success
                Toast.makeText(home.this, "Logout successful", Toast.LENGTH_SHORT).show();

                // Intent to go back to MainActivity
                Intent logoutIntent = new Intent(home.this, MainActivity.class);
                // Clear the activity stack
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish(); // Finish the current activity
            }
        });
    }
}