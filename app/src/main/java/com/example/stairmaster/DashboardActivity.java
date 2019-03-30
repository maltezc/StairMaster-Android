package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stairmaster.adapters.QuestionAdapter;
import com.example.stairmaster.adapters.RecyclerViewAdapter;
import com.example.stairmaster.logins.SignInActivity;
import com.example.stairmaster.models.Question;
import com.example.stairmaster.util.VerticalSpacingItemDecorator;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class DashboardActivity extends AppCompatActivity
//        implements
//        RecyclerViewAdapter.OnNoteListener,
//        View.OnClickListener

{

    static List<String> questionList;
//    ListView questionListView;
    RecyclerView questionListView;
    String answerString;
    TextView textView;
    Button submitQuestionButton;
    Button newQuestionButton;
    TextView textViewData;

    ArrayAdapter arrayAdapter;
    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions
    private static final String TAG = "DashboardActivity";


    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("Questions");
    private static final String KEY_QUESTION_STRING = "Question";
    private static final String KEY_QUESTION_ANSWER_STRING = "Question Answer";


    // UI components
    private RecyclerView mRecyclerView; // m is prepended to global vaiables

    // vars
    private ArrayList<Question> mNotes = new ArrayList<>();
//    private RecyclerViewAdapter mNoteRecyclerAdapter;
    private QuestionAdapter adapter;

    EditText answerEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mRecyclerView = findViewById(R.id.questionRecyclerView);

//        answerEditText = (EditText)findViewById(R.id.answerEditText);
//        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButton);
        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);
//        Toolbar toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        textView = (TextView) findViewById(R.id.textView2);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");

        findViewById(R.id.fab);
        textViewData = findViewById(R.id.text_view_data);

        FloatingActionButton buttonAddQuestion = findViewById(R.id.fab);
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, NewQuestionActivity.class));
            }
        });


        setUpRecyclerView();
//        insertFakeNotes();



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
//        questionRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // this will detach the listener at the appropriate time
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Toast.makeText(DashboardActivity.this, "Error while loading", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, e.toString());
//                    return;
//                }
//
//                if (documentSnapshot.exists()) {
//                    Question question = documentSnapshot.toObject(Question.class);
//
//                    String questionString = question.getQuestion();
//                    String questionAnswerString = question.getAnswer();
//
//                    textViewData.setText("Question: " + questionString + "\n" + "Answer: " + questionAnswerString);
//                } else {
//                    textViewData.setText("");
//                }
//            }
//        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void newQuestionButton(View view) {

        Intent intent = new Intent(DashboardActivity.this, NewQuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, SignInActivity.class));
                break;

            case R.id.menuProfile:
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }

        return true;
    }

// block below will be deleted once firebase is connected.
//    private void insertFakeNotes() {
//        for (int i = 0; i <4; i++){
//            Question note = new Question();
//            note.setTitle("title # " + i);
//            note.setQuestion("question #: " + i);
//            note.setAnswer("answer #: " + i);
//            note.setTimestamp("Jan 2019");
//            mNotes.add(note);
//        }
//        mNoteRecyclerAdapter.notifyDataSetChanged();
//    }

    private void setUpRecyclerView(){
//        Log.d(TAG, "setUpRecyclerView: init recyclerview.");
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
//        mRecyclerView.addItemDecoration(itemDecorator);
//        mNoteRecyclerAdapter = new RecyclerViewAdapter(mNotes, this);
//        mRecyclerView.setAdapter(mNoteRecyclerAdapter);

        Query query = questionRef.orderBy("priority", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
                .build();

        adapter = new QuestionAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public void onNoteClick(int position) {
//        Log.d(TAG, "onNoteClick: clicked" + position);
//
//        //this is where you would navigate to a activity
//
//        Intent intent = new Intent(this, QuestionProfileActivity.class);
//        intent.putExtra("selected_note", mNotes.get(position));
//        startActivity(intent);
//    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, QuestionProfileActivity.class);
//        startActivity(intent);
//    }

    public void loadQuestions(View v) {
//        questionRef.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//
//                            Question question = documentSnapshot.toObject(Question.class);
//
//                            String questionString = question.getQuestion();
//                            String questionAnswerString = question.getAnswer();
//
//                            textViewData.setText("Question: " + questionString + "\n" + "Answer: " + questionAnswerString);
//
//                        } else {
//                            Toast.makeText(DashboardActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(DashboardActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.toString());
//                    }
//                });
    }

//    public void updateAnswer(View view) {
//        String questionAnswerString = answerEditText.getText().toString();
//
//        Map<String, Object> question = new HashMap<>();
//        question.put(KEY_QUESTION_ANSWER_STRING, questionAnswerString);
//
//        // questionRef.set(question, SetOptions.merge());
//        questionRef.update(KEY_QUESTION_ANSWER_STRING, questionAnswerString);
//    }

//    public void deleteAnswerOnly(View view) {
////        Map<String, Object> question = new HashMap<>();
////        question.put(KEY_QUESTION_ANSWER_STRING, FieldValue.delete());
//
////        questionRef.update(question);
//        questionRef.update(KEY_QUESTION_ANSWER_STRING, FieldValue.delete());
//    }

//    public void deleteQuestion(View view){
//        questionRef.delete();
//    }
}
