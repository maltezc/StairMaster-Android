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


    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    ListView questionListView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);


        questionEditText = (EditText)findViewById(R.id.questionEditText);
        answerEditText = (EditText)findViewById(R.id.answerEditText);
        submitQuestionButton = (Button)findViewById(R.id.submitQuestionButton);
        cancelQuestionButton = (Button)findViewById(R.id.cancelQuestionButton);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.activity_dashboard, arrayList);
        questionListView = (ListView)findViewById(R.id.questionListView);

//        questionListView.setAdapter(adapter);

        setTitle("Add a new question");

        Log.i("info", "NewQuestionActivity started");

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        submitQuestionButton();

    }

    public void submitQuestionButton() {

        submitQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String questionEditTextString = questionEditText.getText().toString();
                arrayList.add(questionEditTextString);
                adapter.notifyDataSetChanged();

                Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("questionInfo",questionString);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }


    public void cancelQuestionButton(View view) {

        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



}
