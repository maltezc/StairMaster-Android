package com.example.stairmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class NewQuestionActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    Button submitQuestionButton;
    Button cancelQuestionButton;
    String questionString;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        questionEditText = (EditText)findViewById(R.id.questionEditText);
        answerEditText = (EditText)findViewById(R.id.answerEditText);
        submitQuestionButton = (Button)findViewById(R.id.submitQuestionButton);
        cancelQuestionButton = (Button)findViewById(R.id.cancelQuestionButton);

        setTitle("Add a new question");

        Log.i("info", "NewQuestionActivity started");

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }

    public void submitQuestionButtonClicked(View view) {

        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
        questionString = questionEditText.getText().toString();
        Log.i("info", questionString);
        intent.putExtra("answerText", questionString);
        startActivity(intent);

    }


    public void cancelQuestionButton(View view) {

        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
