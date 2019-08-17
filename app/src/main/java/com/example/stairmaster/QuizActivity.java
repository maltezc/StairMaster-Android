package com.example.stairmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class QuizActivity extends AppCompatActivity {



    TextView quizQuestionCountTextView;
    TextView quizClockTextView;
    TextView quizScoreTextView;
    TextView questionTextTextview;
    RadioButton option1RadioButton;
    RadioButton option2RadioButton;
    RadioButton option3RadioButton;
    RadioButton option4RadioButton;
    RadioButton option5RadioButton;
    RadioButton option6RadioButton;
    Button quizAnswerSubmitButton;
    Button hintsButton;
    Button flagButtonButton;
    Button contributeButton;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestionCountTextView = findViewById(R.id.quizQuestionCount);
        quizClockTextView = findViewById(R.id.quizClock);
        quizScoreTextView = findViewById(R.id.questionTextId);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        option3RadioButton = findViewById(R.id.option3RadioButton);
        option4RadioButton = findViewById(R.id.option4RadioButton);
        option5RadioButton = findViewById(R.id.option5RadioButton);
        option6RadioButton = findViewById(R.id.option6RadioButton);
        quizAnswerSubmitButton = findViewById(R.id.quizAnswerSubmitButton);
        hintsButton = findViewById(R.id.quizHelpButtonId);
        flagButtonButton = findViewById(R.id.quizFlagButtonId);
        contributeButton = findViewById(R.id.quizContributeButtonId);

        mAuth = FirebaseAuth.getInstance();


        // TODO: 2019-08-16 start quiz

        // TODO: 2019-08-16 grab a question from firebase

        // TODO: 2019-08-16 grab answer and other potential answers from firebase

        // TODO: 2019-08-16 submit answer

        // TODO: 2019-08-16 check answer

        // TODO: 2019-08-16 if answer is correct, update score.

        // TODO: 2019-08-16 set max questions to 20 for now.  




    }



}
