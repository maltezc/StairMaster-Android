package com.example.stairmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    TextView questionTextTextview;
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

    private int mScore = 0;
    private int mQuestionNumber = 0;
    private String mAnswer;
    private String mQuestion;

    //Firebase Firestore
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference questionColRef = firestoreDB.collection("Questions");

    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestionCountTextView = findViewById(R.id.quizQuestionCount);
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

        quizScoreTextView.setText("0 /" );






        // TODO: 2019-08-16 start quiz
        // set timer
        //set score
        //

        // TODO: 2019-08-21 grab all questions
        loadQuestionSet();

        // TODO: 2019-08-16 grab a question from firebase

        nextQuestion();


        // TODO: 2019-08-16 if answer exists, grab answer and other potential answers from firebase

        // TODO: 2019-08-16 submit answer

        // TODO: 2019-08-16 check answer

        // TODO: 2019-08-16 if answer is correct, update score.

        // TODO: 2019-08-16 set max questions to 20 for now.




    }

    private void nextQuestion() {
        // get next question in query
        //update score
    }

    private void loadQuestionSet() {
        Query query = questionColRef.orderBy("questionScore"); // field questionPriority is VERY IMPORTANT. if it doesnt match the models category, no items will be displayed.
//        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
//                .setQuery(query, Question.class)
//                .build();

        questionColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> questionList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String questionString = document.get("question").toString();

                        questionList.add(document.get("question").toString());

//                        for(int i = 0; questionList.size() < 20 ; i++ ) {
//                            Log.d(TAG, "onComplete: " + questionList);
//
//
//                        }



//                        questionTextTextview.setText(questionString);

                        /*
                        for(int i = 0; i < stringArray.length; i++){
                            String thisString = stringArray[i];
                            ButtonView thisButton = buttonArray[i];
                            if(thisString.equals(null)){
                                thisButton.setVisibility(View.INVISIBLE);
                            }else{
                                thisButton.setVisibility(View.VISIBLE);
                                thisButton.setText(thisString);
                            }
                        }
                        */
                    }
                    Log.d(TAG, "onComplete: " + questionList);
                    // TODO: 2019-08-21 figure out how to cycle through questions
                    // perhaps grab a batch of questions


//                    for (int i = 0; i < questionList.size(); i++){
//                        questionTextTextview.setText(i);
//                    }

//                    Log.d(TAG, questionList.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: was not able to get a list of questions for Quiz activity");
            }
        });

    }


}
