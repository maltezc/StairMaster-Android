package com.example.stairmaster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stairmaster.models.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewQuestionActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    private NumberPicker numberPickerPriority;
    private EditText editTextTags;
    Button submitQuestionButton;
    Button cancelQuestionButton;
    TextView authorTextView;
    String mUserNameString;
    TextView mDateTimeStampTextView;


    //firebase
    FirebaseAuth mAuth;


    private static final String KEY_QUESTION_TITLE = "Question Title";
    private static final String KEY_QUESTION_STRING = "Question";
    private static final String KEY_QUESTION_ANSWER_STRING = "Question Answer";
    private static final String TAG = "NewQuestionActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        authorTextView = (TextView) findViewById(R.id.authorTextViewId);
        questionEditText = (EditText) findViewById(R.id.questionEditTextId);
        answerEditText = (EditText) findViewById(R.id.answerEditTextId);
        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButtonId);
        cancelQuestionButton = (Button) findViewById(R.id.cancelQuestionButtonId);
        numberPickerPriority = (NumberPicker) findViewById(R.id.number_picker_priorityId);
        editTextTags = findViewById(R.id.edit_text_tags);
        mDateTimeStampTextView = findViewById(R.id.dateTimeStampTextViewId);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        authorTextView.setText(authorFirebase);

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
        String questionString = questionEditText.getText().toString();
//        List<Answer> questionAnswerString;
//        Answer questionAnswerString = answerEditText.getText().toString();
        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();

        //**note** .add will add a new document.
        // .set will create or overwrite.
        // .update will update some of the fields without overwriting the entiredocument

        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userNameString = documentSnapshot.getString("userName");
                authorTextView.setText(userNameString);
            }
        });


        int priority = numberPickerPriority.getValue();
        String tagInput = editTextTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*, \\s*");
        List<String> tags = Arrays.asList(tagArray);
        if (questionString.trim().isEmpty() ) {
            //TODO: DO MORE CHECK FOR SIM OR DUPLICATE

//        if (questionString.trim().isEmpty() || questionAnswerString.trim().isEmpty()) {

            Toast.makeText(this, "Please insert a question.", Toast.LENGTH_SHORT).show();
            return;
        }

        final CollectionReference questionRef = FirebaseFirestore.getInstance().collection("Questions");


        //time stamp block
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.US);
        String datetimeString = formatter.format(date);
        System.out.println("Today : " + datetimeString);
        mDateTimeStampTextView.setText(datetimeString);

        // question doc ref id

        final Question questionInfo = new Question(questionString, priority, tags, authorFirebase, datetimeString);

        questionRef.add(questionInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: questionRef.add executed");
                String questionIdRef = documentReference.getId();
                documentReference.update("questionFirebaseId", questionIdRef).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: questionId added to firebase");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: questionId could not be added to firebase");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: questionRef.add failed");
            }
        });


        Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void submitQuestionButtonClicked(View view) {
        saveQuestion();
        finish();
    }

}