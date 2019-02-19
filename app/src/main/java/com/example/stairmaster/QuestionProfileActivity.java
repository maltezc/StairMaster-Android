package com.example.stairmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class QuestionProfileActivity extends AppCompatActivity {

    Button backButton;
    String answerString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile);

        backButton = (Button)findViewById(R.id.backButton);

        Toolbar toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        answerString = intent.getStringExtra("questionTitle");
        Log.i("info", answerString);
        setTitle(answerString);


//    backButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    });

        //
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finishActivity(1);
            }
        });


    }




}
