package com.example.quizmania;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Simple validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Username validation: must contain at least one letter and one number
        if (!username.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
            Toast.makeText(this, "Username must contain at least one letter and one number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email format validation (only @gmail.com allowed, no special characters before @)
        if (!email.matches("^[a-zA-Z0-9]+@gmail\\.com$")) {
            Toast.makeText(this, "Please enter a valid Gmail address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user with same username or email exists
        if (db.checkUser(username, email)) {
            Toast.makeText(this, "User with this username or email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert user into the database
        boolean isInserted = db.insertUser(username, email, password);
        if (isInserted) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            // Clear the fields
            editTextUsername.setText("");
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
