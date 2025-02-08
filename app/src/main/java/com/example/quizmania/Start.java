package com.example.quizmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button getStartedButton = findViewById(R.id.getStartedButton);
        Button exitButton = findViewById(R.id.exitButton);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, home.class); // Ensure easyqu.class exists
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your exit logic here
                finish(); // This will close the activity
            }
        });
    }
}