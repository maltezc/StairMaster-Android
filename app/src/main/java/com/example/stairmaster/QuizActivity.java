package com.example.stairmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {



    TextView quizQuestionCountTextView;
    TextView quizClockTextView;
    TextView quizScoreTextView;
    TextView questionTextTextView;
    RadioButton option1RadioButton;
    RadioButton option2RadioButton;
    RadioButton option3RadioButton;
    RadioButton option4RadioButton;
    RadioButton option5RadioButton;
    RadioButton option6RadioButton;
    Button quizAnswerSubmitButton;
    Button hintsButton;
    Button flagButtonButton;
    Button contributeButton;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    private int mCounter = 1;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private int mCorrectAnswer = 0;
    private String mAnswer;
    private String mQuestion;
    final List<String> questionList = new ArrayList<>();

    //Firebase Firestore
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference questionColRef = firestoreDB.collection("Questions");

    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestionCountTextView = findViewById(R.id.quizQuestionCount);
        questionTextTextView = findViewById(R.id.questionTextId);
        quizClockTextView = findViewById(R.id.quizClock);
        quizScoreTextView = findViewById(R.id.questionTextId);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        option3RadioButton = findViewById(R.id.option3RadioButton);
        option4RadioButton = findViewById(R.id.option4RadioButton);
        option5RadioButton = findViewById(R.id.option5RadioButton);
        option6RadioButton = findViewById(R.id.option6RadioButton);
        quizAnswerSubmitButton = findViewById(R.id.quizAnswerSubmitButton);
        hintsButton = findViewById(R.id.quizHelpButtonId);
        flagButtonButton = findViewById(R.id.quizFlagButtonId);
        contributeButton = findViewById(R.id.quizContributeButtonId);

        quizScoreTextView.setText(mCorrectAnswer + " / " + mQuestionNumber);


        // TODO: 2019-08-16 start quiz
        // set timer
        //set score
        //

        // TODO: 2019-08-21 grab all questions
        loadQuestionSet();


        // TODO: 2019-08-16 grab a question from quesitions grabbed

        // TODO: 2019-08-16 if answer exists, grab answer and other potential answers from firebase

        // TODO: 2019-08-16 submit answer

        // TODO: 2019-08-16 check answer

        // TODO: 2019-08-16 if answer is correct, update score.

        // TODO: 2019-08-16 set max questions to 20 for now.




    }




    private void loadQuestionSet() {

//        Query first = questionColRef.orderBy("questionScore");
        Query first = questionColRef.orderBy("questionScore").limit(10);


        first.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final int counter = 1;
                DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                questionTextTextView.setText(lastVisible.get("question").toString());
                Log.d(TAG, "onSuccess: loadQuestionSet size " + queryDocumentSnapshots.size());

//                Query next = questionColRef.orderBy("questionScore").startAfter(lastVisible).limit(20);


//                final Query next = questionColRef.orderBy("questionScore").startAfter(lastVisible);
                final Query next = questionColRef.orderBy("questionScore").startAfter(lastVisible).limit(20);
                quizAnswerSubmitButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        next.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - mCounter);
                                questionTextTextView.setText(lastVisible.get("question").toString());
                                Log.d(TAG, "onSuccess: loadQuestionSet size " + queryDocumentSnapshots.size());
                                mCounter++;
                            }
                        });
                    }
                });



            }
        });

//
//        Query query = questionColRef.orderBy("questionScore"); // field questionPriority is VERY IMPORTANT. if it doesnt match the models category, no items will be displayed.
//
////        final List<String> questionList = new ArrayList<>();
//
//        questionColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String questionString = document.get("question").toString();
////                        questionTextTextView.setText(document.get("question").toString());
//
//                        loadQuestion();
//
//                        /*
//                        quizAnswerSubmitButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                loadQuestion();
//                            }
//                        });
//
//                         */
//
////                        int counter = 0;
//                        questionList.add(document.get("question").toString());
////                        questionTextTextView.setText(questionList.get(0)); // sets text to first question in list
////                        questionTextTextView.setText(questionList.get(mCounter)); // sets text to first question in list
////                        questionTextTextView.setText(questionList.listIterator().nextIndex()); // sets text to first question in list
//
//
//                    }
//
//
//                    questionTextTextView.setText(questionList.get(mCounter-1)); // sets text to second question in list
//
//                    quizAnswerSubmitButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mCounter++;
//                            questionTextTextView.setText(questionList.get(mCounter));
//                        }
//                    });
//                    Log.d(TAG, "onComplete: " + questionList);
//                    // TODO: 2019-08-21 figure out how to cycle through questions
//
//
//
////                    for (int i = 0; i < questionList.size(); i++){
////                        questionTextTextView.setText(i);
////                    }
//
////                    Log.d(TAG, questionList.toString());
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: was not able to get a list of questions for Quiz activity");
//            }
//        });
//
//        Log.d(TAG, "loadQuestionSet: " + questionList);

    }

    private void loadQuestion() {
        // get next question in query
        //update score
        Log.d(TAG, "loadQuestion: " + questionList.toString());
        mCounter ++;
//        questionList.get(mCounter);





    }


}
