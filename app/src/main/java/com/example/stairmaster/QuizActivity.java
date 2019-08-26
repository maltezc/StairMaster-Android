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
import java.util.Random;

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


        // TODO: 2019-08-16 grab a question from questions grabbed
        // done.

        setAnswer();

        // TODO: 2019-08-23 set options to potential answers
        // pull random questions' answers for now. in
        // in future, will pull random questions that have the same tag.(one day)

        // assign actual answer to random position 1-6

        // assign random answers to other options in onStart potentially (before shit becomes visible)


        // TODO: 2019-08-16 if answer exists, grab answer and other potential answers from firebase
        // check answer. use parent ID or isChecked parameter


        // TODO: 2019-08-16 submit answer

        // TODO: 2019-08-16 check answer


        // TODO: 2019-08-16 if answer is correct, update score.

        //proceed to next.

        // TODO: 2019-08-16 set max questions to 20 for now.
        //if no more questions, show results.




    }


    private void loadQuestionSet() {

//        Query first = questionColRef.orderBy("questionScore");
        Query first = questionColRef.limit(30);
//        Query first = questionColRef.orderBy("questionScore").limit(10);


        first.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
//                final int counter = 1;
                DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                questionTextTextView.setText(lastVisible.get("question").toString());

                if (lastVisible.contains("answer")) {

                    String correctString = lastVisible.get("answer").toString();
                    option1RadioButton.setText(correctString);

                } else {
                    option1RadioButton.setText("No answer exists for this question yet");

                }


//                setAnswer();

                quizQuestionCountTextView.setText(mCounter + " / " + queryDocumentSnapshots.size());

                Log.d(TAG, "onSuccess: loadQuestionSet size " + queryDocumentSnapshots.size());

                quizAnswerSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - mCounter);
                        questionTextTextView.setText(lastVisible.get("question").toString());
                        Log.d(TAG, "onSuccess: loadQuestionSet size " + queryDocumentSnapshots.size());
                        mCounter++;
//                        setAnswer();

                        quizQuestionCountTextView.setText(mCounter + " / " + queryDocumentSnapshots.size());
                        Log.d(TAG, "onSuccess: counter is at " + mCounter);
                    }
                });
            }
        });
    }

    private void loadQuestion() {
        // get next question in query
        //update score
        Log.d(TAG, "loadQuestion: " + questionList.toString());
        mCounter ++;
//        questionList.get(mCounter);

    }

    private void setAnswer() {
        String correctString = "testCorrectString";
        String inCorrectString1 = "testIncorrectString1";
        String inCorrectString2 = "testIncorrectString2";
        String inCorrectString3 = "testIncorrectString3";
        String inCorrectString4 = "testIncorrectString4";
        String inCorrectString5 = "testIncorrectString5";


        Random rand = new Random();
        int n = rand.nextInt(6);
        Log.d(TAG, "setAnswer: randomInt = " + n);

        switch (n) {
            case 0:
                // test1
                option1RadioButton.setText(correctString);
                option2RadioButton.setText(inCorrectString1);
                option3RadioButton.setText(inCorrectString2);
                option4RadioButton.setText(inCorrectString3);
                option5RadioButton.setText(inCorrectString4);
                option6RadioButton.setText(inCorrectString5);
                break;
            case 1:
                // test1
                option1RadioButton.setText(inCorrectString1);
                option2RadioButton.setText(correctString);
                option3RadioButton.setText(inCorrectString2);
                option4RadioButton.setText(inCorrectString3);
                option5RadioButton.setText(inCorrectString4);
                option6RadioButton.setText(inCorrectString5);
                break;

            case 2:
                //test2
                option1RadioButton.setText(inCorrectString1);
                option2RadioButton.setText(inCorrectString2);
                option3RadioButton.setText(correctString);
                option4RadioButton.setText(inCorrectString3);
                option5RadioButton.setText(inCorrectString4);
                option6RadioButton.setText(inCorrectString5);
                break;
            case 3:
                //test3
                option1RadioButton.setText(inCorrectString1);
                option2RadioButton.setText(inCorrectString2);
                option3RadioButton.setText(inCorrectString3);
                option4RadioButton.setText(correctString);
                option5RadioButton.setText(inCorrectString4);
                option6RadioButton.setText(inCorrectString5);
                break;
            case 4:
                // test4
                option1RadioButton.setText(inCorrectString1);
                option2RadioButton.setText(inCorrectString2);
                option3RadioButton.setText(inCorrectString3);
                option4RadioButton.setText(inCorrectString4);
                option5RadioButton.setText(correctString);
                option6RadioButton.setText(inCorrectString5);
                break;
            case 5:
                // test5
                option1RadioButton.setText(inCorrectString1);
                option2RadioButton.setText(inCorrectString2);
                option3RadioButton.setText(inCorrectString3);
                option4RadioButton.setText(inCorrectString4);
                option5RadioButton.setText(inCorrectString5);
                option6RadioButton.setText(correctString);
                break;


        }

//        if(n == 0){
//            answer1.setText(correctString);
//            answer2.setText(inCorrectString);
//        }else{
//            answer1.setText(inCorrectString);
//            answer2.setText(correctString);
//        }
    }


}
