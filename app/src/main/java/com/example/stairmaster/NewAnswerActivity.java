package com.example.stairmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stairmaster.models.Answer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewAnswerActivity extends AppCompatActivity {

    TextView questionContentTextView;
    TextView questionAuthorTextView;
    TextView dateTimeStampTextView;
    TextView answerEditText;
    TextView answerAuthorTextView;
    Button postAnswerButton;
    Button saveDraftButton;
    Button uploadImageButton;
    Button cancelAnswerButton;

    FirebaseAuth mAuth;

    private static final String TAG = "NewAnswerActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_answer);

        questionContentTextView = findViewById(R.id.questionContentTextViewId);
        questionAuthorTextView = findViewById(R.id.questionAuthorTextViewId);
        dateTimeStampTextView = findViewById(R.id.dateTimeStampTextViewId);
        answerEditText = findViewById(R.id.answerEditTextId);
        postAnswerButton = findViewById(R.id.postAnswerButtonId);
        answerAuthorTextView = findViewById(R.id.answerAuthorTextViewId);
        saveDraftButton = findViewById(R.id.saveDraftButtonId);
        uploadImageButton = findViewById(R.id.uploadImageButtonId);
        cancelAnswerButton = findViewById(R.id.cancelAnswerButtonId);


        mAuth = FirebaseAuth.getInstance();

        // add if else check
        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        loadQuestionContent();

        loadAnswerContentUser();

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

    private void loadQuestionContent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");

        if (getIntent().hasExtra("question_string")) {

            String questionString = (String) getIntent().getExtras().get("question_string").toString();
            String questionAuthorString = (String) getIntent().getExtras().get("questionAuthor_string").toString();
            String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

            questionContentTextView.setText(questionString);
            questionAuthorTextView.setText(questionAuthorString);

        }
    }




    private void loadAnswerContentUser(){
        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();

        String authorFirebase = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userNameString = documentSnapshot.getString("userName");

                answerAuthorTextView.setText(userNameString);
            }
        });
    }


    private void saveAnswer() {
        //TODO: Add answer to question.

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.US);
        String datetimeString = formatter.format(date);
        System.out.println("Today : " + datetimeString);

//        String dateTime = getDateTime();
        String answerString = answerEditText.getText().toString();
        String answerAuthor = answerAuthorTextView.getText().toString();
        dateTimeStampTextView.setText(datetimeString);

        loadQuestionContent();

        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");



        Log.d(TAG, "saveAnswer: clicked");


        final Answer answerInfo = new Answer(answerString, answerAuthor, datetimeString, questionPathIDString);

        //TODO: FIGURE OUT HOW TO SET ANSWER



        //code below is reference code
//        questionRef.document().set(questionInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: QuestionRef.set executed");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: questionRef.set failed");
//            }
//        });




    }

    private void uploadImage() {
    }

    private void saveDraft() {
    }

    private void cancelAnswer() {



    }

    //TODO: Set recycler view for answers in question Profile










}
