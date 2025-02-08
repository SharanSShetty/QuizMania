package com.example.quizmania;

import android.content.Intent;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class c2answer extends AppCompatActivity {

    private String[] questions;
    private String[] userAnswers;
    private String[] correctAnswers;
    private int currentIndex = 0;
    private int totalQuestions;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        // Retrieve data from the Intent
        questions = getIntent().getStringArrayExtra("questions");
        userAnswers = getIntent().getStringArrayExtra("userAnswers");
        correctAnswers = getIntent().getStringArrayExtra("correctAnswers");

        // Check if data is available
        if (questions == null || userAnswers == null || correctAnswers == null) {
            // Handle the case where the data is not passed
            return; // or show an error message
        }

        // Set the total number of questions
        totalQuestions = questions.length;

        // Initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100); // Max value for the progress bar

        // Initialize the UI elements
        TextView questionText = findViewById(R.id.question_text);
        TextView userAnswerText = findViewById(R.id.user_answer_text);
        TextView correctAnswerText = findViewById(R.id.correct_answers_text);

        Button nextButton = findViewById(R.id.next_button);
        Button previousButton = findViewById(R.id.previous_button);
        Button homeButton = findViewById(R.id.home);

        // Display the current question and answers with transition effects
        displayCurrentQuestion(questionText, userAnswerText, correctAnswerText);

        // Set up the "Next" button
        nextButton.setOnClickListener(v -> {
            if (currentIndex < totalQuestions - 1) {
                currentIndex++;
                displayCurrentQuestion(questionText, userAnswerText, correctAnswerText);
            }
        });

        // Set up the "Previous" button
        previousButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayCurrentQuestion(questionText, userAnswerText, correctAnswerText);
            }
        });

        // Set up the "Home" button
        homeButton.setOnClickListener(v -> home());
    }

    // Method to display current question and answers
    private void displayCurrentQuestion(TextView questionText, TextView userAnswerText, TextView correctAnswerText) {
        // Fade out the current question and answers
        fadeOut(questionText);
        fadeOut(userAnswerText);
        fadeOut(correctAnswerText);

        // Update the text values
        questionText.setText(questions[currentIndex]);
        userAnswerText.setText("Your Answer: " + userAnswers[currentIndex]);
        correctAnswerText.setText("Correct Answer: " + correctAnswers[currentIndex]);

        // Fade in the new question and answers
        fadeIn(questionText);
        fadeIn(userAnswerText);
        fadeIn(correctAnswerText);

        // Update the progress bar
        updateProgressBar();

        // Disable or enable buttons based on current index
        Button nextButton = findViewById(R.id.next_button);
        Button previousButton = findViewById(R.id.previous_button);

        // Disable the next button if it's the last question
        nextButton.setEnabled(currentIndex < totalQuestions - 1);

        // Disable the previous button if it's the first question
        previousButton.setEnabled(currentIndex > 0);
    }

    // Method to update progress bar
    private void updateProgressBar() {
        // If it's the last question, set the progress to 100
        int progress = (currentIndex == totalQuestions - 1) ? 100 : (int) ((currentIndex / (float) totalQuestions) * 100);
        progressBar.setProgress(progress);
    }

    // Fade-out animation for views
    private void fadeOut(View view) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        fadeOut.setDuration(500); // Duration of the fade-out animation
        fadeOut.start();
    }

    // Fade-in animation for views
    private void fadeIn(View view) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(500); // Duration of the fade-in animation
        fadeIn.start();
    }

    // Home button method to navigate to home activity
    private void home() {
        // Go to home activity
        Intent intent = new Intent(c2answer.this, home.class);
        startActivity(intent);
    }
}
