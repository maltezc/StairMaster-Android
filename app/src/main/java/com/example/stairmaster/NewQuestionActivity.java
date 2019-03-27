package com.example.stairmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewQuestionActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    Button submitQuestionButton;
    Button cancelQuestionButton;
    String questionString;

    private static final String KEY_QUESTION_TITLE = "Question Title";
    private static final String KEY_QUESTION_STRING = "Question";
    private static final String KEY_QUESTION_ANSWER_STRING = "Question Answer";
    private static final String TAG = "NewQuestionActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference questionRef = db.document("Questions/My First Question");



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

    public void saveQuestion(View view){
//        String questionTitle = questionEditText.getText().toString(); //TODO: figure out title situation
        String questionString = questionEditText.getText().toString();
        String questionAnswerString = answerEditText.getText().toString();

        Map<String, Object> question = new HashMap<>();
//        question.put(KEY_QUESTION_TITLE, questionTitle);
        question.put(KEY_QUESTION_STRING, questionString);
        question.put(KEY_QUESTION_ANSWER_STRING, questionAnswerString);

//        db.document("Questions/My First Question");
       questionRef.set(question)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NewQuestionActivity.this, "Question Saved", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewQuestionActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                        
                    }
                });


    }

//    public void submitQuestionButtonClicked(View view) {
//
//        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
//        questionString = questionEditText.getText().toString();
//        Log.i("info", questionString);
//        intent.putExtra("answerText", questionString);
//        setResult(RESULT_OK, intent);
//        finish();
//
//    }


    public void cancelQuestionButton(View view) {

        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
