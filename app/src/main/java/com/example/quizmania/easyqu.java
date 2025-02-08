package com.example.quizmania;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class easyqu extends AppCompatActivity {

    TextView questionText, questionNumberText, timerText;
    RadioGroup radioGroupOptions;
    RadioButton option1, option2, option3, option4;
    Button nextButton, submitButton, previousButton;
    int currentQuestionIndex = 0;
    int score = 0;
    String[] questions = {
            "What is the correct syntax to output 'Hello World' in Python?",
            "Which of the following is a mutable data type in Python?",
            "What is the output of the following code? print(3 * '7')",
            "Which of the following functions can convert a string into an integer in Python?",
            "What does the len() function return in Python?",
            "Which keyword is used to define a function in Python?",
            "What is the output of the following code? x = [1, 2, 3] print(x[1])",
            "How can you remove an element from a list in Python by index?",
            "What will the following expression return? 5 // 2",
            "Which operator is used to check for equality in Python?"
    };

    String[][] options = {
            {"print('Hello World')", "echo 'Hello World'", "console.log('Hello World')", "System.out.println('Hello World')"},
            {"Tuple", "String", "List", "Set"},
            {"777", "21", "7777777", "999"},
            {"float()", "str()", "int()", "bool()"},
            {"The number of characters in a string", "The size of a file", "Both A and C", "None of the above"},
            {"func", "function", "def", "procedure"},
            {"1", "2", "3", "4"},
            {"remove()", "pop()", "del", "clear()"},
            {"2", "2.5", "2.0", "3.0"},
            {"=", "==", "!=", "==="}
    };

    String[] correctAnswers = {"print('Hello World')",
            "List",
            "777",
            "int()",
            "Both A and C",
            "def",
            "2",
            "del",
            "2",
            "=="
    };
    String[] userAnswers = new String[questions.length];

    // Timer variables
    private static final long TOTAL_TIME_LIMIT = 300000; // 300 seconds (5 minutes) for the entire quiz
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = TOTAL_TIME_LIMIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easyqu);

        // Initialize UI elements
        initializeUI();

        // Load the first question
        loadQuestion();

        // Set up button listeners
        setUpListeners();

        // Start the global timer
        startTimer();
    }

    private void initializeUI() {
        questionText = findViewById(R.id.question_text);
        questionNumberText = findViewById(R.id.question_number_text);
        timerText = findViewById(R.id.timer_text); // TextView for displaying the timer
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.next_button);
        submitButton = findViewById(R.id.submit_button);
        previousButton = findViewById(R.id.previous_button);
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        questionNumberText.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.length);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);
        radioGroupOptions.clearCheck();

        // Restore previously selected answer if available
        if (userAnswers[currentQuestionIndex] != null) {
            for (int i = 0; i < radioGroupOptions.getChildCount(); i++) {
                RadioButton rb = (RadioButton) radioGroupOptions.getChildAt(i);
                if (rb.getText().toString().equals(userAnswers[currentQuestionIndex])) {
                    rb.setChecked(true);
                    break;
                }
            }
        }

        // Disable the previous button if the user is on the first question
        if (currentQuestionIndex == 0) {
            previousButton.setEnabled(false);
        } else {
            previousButton.setEnabled(true);
        }

        // Disable the next button and show submit button if on the last question
        if (currentQuestionIndex == questions.length - 1) {
            nextButton.setVisibility(View.INVISIBLE);  // Hide next button
            submitButton.setVisibility(View.VISIBLE);  // Show submit button
        } else {
            nextButton.setVisibility(View.VISIBLE);   // Show next button
            submitButton.setVisibility(View.INVISIBLE); // Hide submit button
        }
    }

    private void setUpListeners() {
        nextButton.setOnClickListener(v -> {
            saveUserAnswer();
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadQuestion();
            }
        });

        previousButton.setOnClickListener(v -> {
            saveUserAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        submitButton.setOnClickListener(v -> {
            saveUserAnswer();
            calculateScore();
            Intent intent = new Intent(easyqu.this, ViewResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("totalQuestions", questions.length);
            intent.putExtra("questions", questions);
            intent.putExtra("userAnswers", userAnswers); // Pass user answers
            intent.putExtra("correctAnswers", correctAnswers); // Pass correct answers
            startActivity(intent);
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Time is up, move to the result screen automatically
                submitButton.performClick();
            }
        }.start();
    }

    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeFormatted = String.format("%02d:%02d", seconds / 60, seconds % 60);
        timerText.setText(timeFormatted);
    }

    private void saveUserAnswer() {
        int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedOptionId != -1) {
            RadioButton selectedOption = findViewById(selectedOptionId);
            userAnswers[currentQuestionIndex] = selectedOption.getText().toString();
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (correctAnswers[i].equals(userAnswers[i])) {
                score += 2;
            }
        }
    }
}
