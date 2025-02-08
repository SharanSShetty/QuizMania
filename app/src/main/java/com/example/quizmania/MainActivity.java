package com.example.quizmania;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this); // Initialize the database

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        Button signupText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // Validate login credentials using the database
                if (db.checkLogin(user, pass)) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to HomeActivity
                    Intent intent = new Intent(MainActivity.this, Start.class);
                    startActivity(intent);
                    finish(); // Optional: Close the login activity so the user can't go back to it with the back button
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed! Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });
    }
}
