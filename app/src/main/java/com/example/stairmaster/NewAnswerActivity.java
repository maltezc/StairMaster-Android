package com.example.stairmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewAnswerActivity extends AppCompatActivity {

    TextView questionContent;
    TextView authorTextView;
    TextView dateTimeStampTextView;
    TextView answerEditText;
    Button postAnswerButton;
    Button saveDraftButton;
    Button uploadImageButton;
    Button cancelAnswerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_answer);

        questionContent = findViewById(R.id.questionContentID);
        dateTimeStampTextView = findViewById(R.id.dateTimeStampTextViewID);
        answerEditText = findViewById(R.id.postAnswerButtonID);
        postAnswerButton = findViewById(R.id.postAnswerButtonID);
        saveDraftButton = findViewById(R.id.saveDraftButtonID);
        uploadImageButton = findViewById(R.id.uploadImageButtonID);
        cancelAnswerButton = findViewById(R.id.cancelQuestionButtonID);


        postAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();

            }
        });

        saveDraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDraft();
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        cancelAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAnswer();
            }
        });
    }


    private void saveAnswer() {
        //TODO: Add answer to question.
    }

    private void uploadImage() {
    }

    private void saveDraft() {
    }

    private void cancelAnswer() {
    }

    //TODO: Set recycler view for answers in question Profile










}
