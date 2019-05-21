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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class NewQuestionActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    private NumberPicker numberPickerPriority;
    private EditText editTextTags;
    Button submitQuestionButton;
    Button cancelQuestionButton;
    TextView authorTextView;
    String mUserNameString;

    //firebase
    FirebaseAuth mAuth;


    private static final String KEY_QUESTION_TITLE = "Question Title";
    private static final String KEY_QUESTION_STRING = "Question";
    private static final String KEY_QUESTION_ANSWER_STRING = "Question Answer";
    private static final String TAG = "NewQuestionActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        authorTextView = (TextView) findViewById(R.id.authorTextViewID);
        questionEditText = (EditText) findViewById(R.id.questionEditTextID);
        answerEditText = (EditText) findViewById(R.id.answerEditTextID);
        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButtonID);
        cancelQuestionButton = (Button) findViewById(R.id.cancelQuestionButtonID);
        numberPickerPriority = (NumberPicker) findViewById(R.id.number_picker_priorityID);
        editTextTags = findViewById(R.id.edit_text_tags);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName(); //TODO: HOW TO SET THIS Author
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
        String questionAnswerString = answerEditText.getText().toString();
        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();

        //**note** .add will add a new document.
        // .set will create or overwrite.
        // .update will update some of the fields without overwriting the entiredocument

        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users"); //TODO: THIS SHOULD BE COLLECTING THE QUESTIONS AUTHOR. FIGURE OUT HOW TO SET
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userNameString = documentSnapshot.getString("userName");

                authorTextView.setText(userNameString);
//                authorFirebase = userNameString;
            }
        });

        int priority = numberPickerPriority.getValue();
        String tagInput = editTextTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*, \\s*");
        List<String> tags = Arrays.asList(tagArray);
        if (questionString.trim().isEmpty() ) {
//        if (questionString.trim().isEmpty() || questionAnswerString.trim().isEmpty()) {

            Toast.makeText(this, "Please insert a question.", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference questionRef = FirebaseFirestore.getInstance().collection("Questions");
//        questionRef.add(new Question(questionString, questionAnswerString, priority, tags, authorFirebase)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d(TAG, "onSuccess: questionRef.add was executed");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: questionref.add failed");
//            }
//        });




///////Code block below is a test
        final Question questionInfo = new Question(questionString, questionAnswerString, priority, tags, authorFirebase);

        questionRef.document().set(questionInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//        questionRef.document().set(questionInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: QuestionRef.set executed");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: questionRef.set failed");
            }
        });
////////End of code block test

//        rootRef.collection("Users").document(userEmail).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: user created");
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: " + e.toString());
//            }
//        });



        Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void submitQuestionButtonClicked(View view) {
        saveQuestion();
    }

}