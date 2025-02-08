package com.example.quizmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class pylevel extends AppCompatActivity {

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pylevel); // Ensure you have activity_pylevel layout file

        // Initialize buttons
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);

        // Set button click listeners to open different activities
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pylevel.this, easyqu.class); // Ensure easyqu.class exists
                startActivity(intent);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pylevel.this, python2.class); // Ensure Medium.class exists
                startActivity(intent);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pylevel.this, python3.class); // Ensure Hard.class exists
                startActivity(intent);
            }
        });
    }
}
