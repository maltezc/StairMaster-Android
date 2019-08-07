package com.example.stairmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stairmaster.models.Answer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

                finish();

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

        /// TODO: 2019-08-05 user must be logged in to provide an answer

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.US);
        String datetimeString = formatter.format(date);
        System.out.println("Today : " + datetimeString);
        int score = 0;
        String scoreString = Integer.toString(score);

//        String dateTime = getDateTime();
        String answerString = answerEditText.getText().toString();
        String answerAuthor = answerAuthorTextView.getText().toString();

        dateTimeStampTextView.setText(datetimeString);

        loadQuestionContent();

        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

        final Answer answerInfo = new Answer(answerString, datetimeString, answerAuthor, questionPathIDString, score);

        final String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
        final CollectionReference usersCollectionRef = FirebaseFirestore.getInstance().collection("Users");
        final DocumentReference userDocRef = usersCollectionRef.document(userFirebaseEmail); // <-- this works!****
        final String userDocRefId = usersCollectionRef.document(userFirebaseEmail).getId(); // <-- this works!****
        final CollectionReference answerRef = FirebaseFirestore.getInstance().collection("Answers");


        answerRef.add(answerInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: Answer was added");
                String collectionType = "Answer";

                final String answerRefId  = documentReference.getId();
                documentReference.update(
                        "answerFirebaseId", answerRefId,
                        "collectionType", collectionType).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: answerFirebaseId added to database");

                        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
                        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Users").document(userFirebaseEmail);
//                        String answer = documentReference.getId();
//                        answerRefId
                        userDocRef.update("actionHistory", FieldValue.arrayUnion(answerRefId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: actionHistory update succeeded");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: actionhistory update Failed" + e);
                            }
                        });


                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());

            }
        });

    }

    private void uploadImage() {
    }

    private void saveDraft() {
    }

    private void cancelAnswer() {



    }

    //TODO: Set recycler view for answers in question Profile










}
