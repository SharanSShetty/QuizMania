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

public class c3 extends AppCompatActivity {

    TextView questionText, questionNumberText, timerText;
    RadioGroup radioGroupOptions;
    RadioButton option1, option2, option3, option4;
    Button nextButton, submitButton, previousButton;
    int currentQuestionIndex = 0;
    int score = 0;
    String[] questions = {
            "Which of the following is a correct way to overload a function in C++?",
            "What is the output of the following code? int arr[] = {1, 2, 3}; cout << arr[1];",
            "How do you access the first element of a pointer array in C++?",
            "Which of the following is a correct syntax for inheritance in C++?",
            "What is the output of the following code? char c = 'A'; cout << (int)c;",
            "Which of the following is used to catch exceptions in C++?",
            "What does the 'new' keyword do in C++?",
            "What is the difference between 'struct' and 'class' in C++?",
            "Which of the following loops is guaranteed to execute at least once?",
            "How do you declare a friend function in C++?"
    };

    String[][] options = {
            {"Define multiple functions with the same name", "Define a function inside another function", "Define a function inside a class", "Define a function with no return type"},
            {"1", "2", "3", "Error"},
            {"arr[0]", "*arr", "arr", "arr->first()"},
            {"class Derived : public Base", "Derived(Base)", "Base class Derived", "inherit Base Derived"},
            {"65", "'A'", "Error", "'B'"},
            {"catch", "throw", "except", "trap"},
            {"Allocates memory", "Deletes memory", "Initializes a variable", "Assigns a value"},
            {"struct members are public by default", "struct supports inheritance", "No difference", "class supports multiple inheritance, struct does not"},
            {"for", "while", "do-while", "switch"},
            {"friend funcName()", "friend class funcName()", "funcName() friend", "friendship funcName()"}
    };

    String[] correctAnswers = {"Define multiple functions with the same name", "2", "*arr", "class Derived : public Base", "65", "catch", "Allocates memory", "struct members are public by default", "do-while", "friend funcName()"};
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
            Intent intent = new Intent(c3.this, c3result.class);
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
