package com.example.stairmaster;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.stairmaster.models.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class NewQuestionActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    private NumberPicker numberPickerPriority;
    Button submitQuestionButton;
    Button cancelQuestionButton;

    private static final String KEY_QUESTION_TITLE = "Question Title";
    private static final String KEY_QUESTION_STRING = "Question";
    private static final String KEY_QUESTION_ANSWER_STRING = "Question Answer";
    private static final String TAG = "NewQuestionActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        questionEditText = (EditText) findViewById(R.id.questionEditTextID);
        answerEditText = (EditText) findViewById(R.id.answerEditTextID);
        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButtonID);
        cancelQuestionButton = (Button) findViewById(R.id.cancelQuestionButtonID);
        numberPickerPriority = (NumberPicker) findViewById(R.id.number_picker_priorityID);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

//        setTitle("Add a new question");

        Log.i("info", "NewQuestionActivity started");


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add Question");


        submitQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuestionButtonClicked(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_question_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_question:
                saveQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveQuestion(){
//        String questionTitle = questionEditText.getText().toString(); //TODO: figure out title situation
        String questionString = questionEditText.getText().toString();
        String questionAnswerString = answerEditText.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (questionString.trim().isEmpty() || questionAnswerString.trim().isEmpty()) {

            Toast.makeText(this, "Please insert a question and a proposed answer", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference questionRef = FirebaseFirestore.getInstance()
                .collection("Questions");
        questionRef.add(new Question(questionString, questionAnswerString, priority));
        Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void submitQuestionButtonClicked(View view) {

        saveQuestion();

    }

}