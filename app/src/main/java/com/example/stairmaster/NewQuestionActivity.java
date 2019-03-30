package com.example.stairmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    String questionString;

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

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);


//        //TODO: FIGURE OUT TOOLBAR ISSUE. NOTE THAT IF YOU HIT SUBMIT, APP WILL CRASH, MUST USE SAVE ICON IN TOOLBAR
//        if (getSupportActionBar() != null) {
//
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
//            setTitle("Add Question");
//        }
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

//        Question question = new Question(questionString, questionAnswerString);

//       questionRef.set(question)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(NewQuestionActivity.this, "Question Saved", Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(NewQuestionActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.toString());
//
//
//                    }
//                });
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


//    public void cancelQuestionButton(View view) {
//
//        Intent intent = new Intent(NewQuestionActivity.this, DashboardActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//
//    }
}