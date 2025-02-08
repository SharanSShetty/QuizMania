package com.example.quizmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class c1result extends AppCompatActivity {  // Renamed to ResultActivity for clarity

    private static final int PASSING_SCORE = 7;
    private static final int MARKS_PER_QUESTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        // Initialize the UI elements
        TextView resultMessage = findViewById(R.id.view_result_message);
        TextView scoreDisplay = findViewById(R.id.score_display);
        Button viewCorrectAnswersButton = findViewById(R.id.view_correct_answers_button);

        // Get the score and total questions from the intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        int totalPossibleScore = totalQuestions * MARKS_PER_QUESTION;

        // Display pass or fail message based on the score
        String message = (score >= PASSING_SCORE)
                ? "Congratulations! You have passed."
                : "You failed. Better luck next time.";

        // Set the pass/fail message and text color
        resultMessage.setText(message);
        if (score >= PASSING_SCORE) {
            resultMessage.setTextColor(getResources().getColor(R.color.pass_color));  // Green for pass
        } else {
            resultMessage.setTextColor(getResources().getColor(R.color.fail_color));  // Red for fail
        }

        // Display the score
        String scoreText = String.format("You scored %d out of %d", score, totalPossibleScore);
        scoreDisplay.setText(scoreText);

        // Set click listener for the button to view correct answers
        viewCorrectAnswersButton.setOnClickListener(v -> {
            Intent intent = new Intent(c1result.this, canswer.class);
            intent.putExtra("questions", getIntent().getStringArrayExtra("questions")); // Pass questions
            intent.putExtra("userAnswers", getIntent().getStringArrayExtra("userAnswers")); // Pass user answers
            intent.putExtra("correctAnswers", getIntent().getStringArrayExtra("correctAnswers")); // Pass correct answers
            startActivity(intent);
        });
    }
}
