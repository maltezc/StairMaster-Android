package com.example.stairmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stairmaster.adapters.AnswerAdapter;
import com.example.stairmaster.models.Answer;
import com.example.stairmaster.models.Question;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class QuestionProfileActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

    private static final String TAG = "QuestionProfileActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //firebase
    FirebaseAuth mAuth;


    // ui components
    private EditText mQuestionEditText;
    private TextView mQuestionTextView;
    private RecyclerView mAnswerRecyclerView;
    private EditText mQuestionAnswerEditText;
    private TextView mQuestionPriorityContent;
    private TextView mQuestionAuthorTextView;
    private TextView mDateTimeStampTextView;

    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck;
    private ImageButton mBackArrow;

//    private ImageButton mSaveButton;
    private ImageButton mEditButton;
    private Button mPostAnswerButton;
    private Button mCommentButton;
    private ImageButton mSaveButton;
    private Button mDeleteQuestionButton;
    private ImageButton mAnswerUpVoteButton;
    private ImageButton mAnswerDownVoteButton;


    //vars
    private GestureDetector mGestureDetector;
    private boolean mShowSaveIcon;
    private String mQuestionFirebaseId;

    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference answerRef = firestoreDB.collection("Answers");

    private String questionPathIDString;
    private ArrayList<Answer>mAnswers = new ArrayList<>();
    private AnswerAdapter mAnswerRecyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile);

        firestoreDB = FirebaseFirestore.getInstance();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        setTitle("Question");

        mQuestionTextView = findViewById(R.id.questionTextViewId);
        mQuestionAnswerEditText = findViewById(R.id.answerEditTextId);
        mQuestionPriorityContent = findViewById(R.id.starTextViewId);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mEditButton = findViewById(R.id.edit_question);
        mSaveButton = findViewById(R.id.save_question);
        mPostAnswerButton = findViewById(R.id.postAnswerButtonId);
        mCommentButton = findViewById(R.id.commentButtonId);
        mDeleteQuestionButton = findViewById(R.id.deleteQuestionButtonId);
        mQuestionAuthorTextView = findViewById(R.id.questionAuthorTextViewId);
        mDateTimeStampTextView = findViewById(R.id.dateTimeStampTextViewId);
        mAnswerUpVoteButton = findViewById(R.id.answerUpVoteId);
        mAnswerDownVoteButton = findViewById(R.id.answerDownVoteId);


        mAnswerRecyclerView = (RecyclerView) findViewById(R.id.answerRecyclerViewId);

        getIncomingIntent();
        setUpAnswerRecyclerView();



        mPostAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAnswerButtonClicked();
                //TODO:  send user to new activity through intent.
                //TODO: be able to upload photo
                //TODO: look at preview

            }
        });

        mDeleteQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuestion();
                finish();

            }
        });

        /*Cant do this because you need to split it up in to fragments
        mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upVoteClicked();
            }
        });


        mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downVoteClicked();
            }
        });
        */
    }

    private void upVoteClicked() {
        Log.d(TAG, "upVoteClicked: Up Vote Clicked");

    }

    private void downVoteClicked() {
        Log.d(TAG, "downVoteClicked: DownVote Clicked");

    }

    private void deleteQuestion() {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionRootRef = rootRef.collection("Questions");
        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");
        DocumentReference docRef = questionRootRef.document(questionPathIDString); // <-- this works!****
        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Question was successfully deleted");
                Toast.makeText(QuestionProfileActivity.this, "Question was successfully deleted", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

    }

    private void setUpAnswerRecyclerView() {
//        final String userName = mAuth.getCurrentUser().getDisplayName();
//        Query query = answerRef.whereEqualTo("author", userName);

        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");
        Query query = answerRef.whereEqualTo("parentQuestionId", questionPathIDString);
//        Query query = answerRef.orderBy("score", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Answer> options = new FirestoreRecyclerOptions.Builder<Answer>()
                .setQuery(query, Answer.class)
                .build();

        mAnswerRecyclerViewAdapter = new AnswerAdapter(options);

        RecyclerView answerRecyclerView = findViewById(R.id.answerRecyclerViewId);
        answerRecyclerView.setHasFixedSize(true);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerRecyclerView.setAdapter(mAnswerRecyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mAnswerRecyclerViewAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(answerRecyclerView);

    }

    private void postAnswerButtonClicked() {

        getIncomingIntent();

        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

        Intent intent = new Intent(QuestionProfileActivity.this, NewAnswerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("question_string", mQuestionTextView.getText());
        intent.putExtra("questionAuthor_string", mQuestionAuthorTextView.getText());
        intent.putExtra("questionID_string", questionPathIDString);

        startActivity(intent);
    }


    private void setListeners(){

        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public void onStart() {  // NEED THIS TO MAKE RECYCLER VIEW WORK **VERY IMPORTANT**
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final FirebaseUser firebaseUser = task.getResult().getUser();
        mAnswerRecyclerViewAdapter.startListening();
    }

    @Override
    protected void onStop() { // NEED THIS TO MAKE RECYCLER VIEW WORK **VERY IMPORTANT**
        super.onStop();
        mAnswerRecyclerViewAdapter.stopListening();
    }

    private void getIncomingIntent() {

        Log.d(TAG, "getIncomingIntent: checking for incoming intent");

        if (getIntent().hasExtra("question_string")) {

            Log.d(TAG, "getIncomingIntent: " + mQuestionTextView.toString());
            String questionString = (String) getIntent().getExtras().get("question_string");
            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
            String questionPriorityString = (String) getIntent().getExtras().get("questionPriority_string");
            String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");
            String questionAuthorString = (String) getIntent().getExtras().get("questionAuthorString");
            String questionDateTimeStamp = (String) getIntent().getExtras().get("questionTimestampString");

            mDateTimeStampTextView.setText(questionDateTimeStamp);
            mQuestionAuthorTextView.setText(questionAuthorString);
            mQuestionTextView.setText(questionString);
            mQuestionPriorityContent.setText(questionPriorityString);


            System.out.println(questionPathIDString);
        }
    }


    private void hideSoftKeyboard() {
        //keyboard in android is referred to as the Soft Keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!");
//        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.toolbar_check: {
                hideSoftKeyboard();
//                disableEditMode();
                break;
            }

            case R.id.note_text_title: {
                break;
            }
        }
    }


    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemSave = menu.findItem(R.id.save_question);
        itemSave.setVisible(mShowSaveIcon);
        menu.findItem(R.id.edit_question).setVisible(!mShowSaveIcon);  // you can use negation of the same flag if one and only one of two menu items is visible; or create more complex logic

//        MenuItem itemEdit = menu.findItem(R.id.edit_question);
//        itemEdit.setVisible(mShowSaveIcon);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_question_menu, menu);
        MenuItem itemSave = menu.findItem(R.id.save_question);
        itemSave.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_question:
                mShowSaveIcon = false;
                updateQuestion();
                break;

            case R.id.edit_question:
                item.setVisible(false);
                enableEditMode();
                mShowSaveIcon = true;
                break;

        }
        invalidateOptionsMenu();
        return true;
    }

    private void enableEditMode(){

        // disables question edit text and post answer button and darkens buttons
        mQuestionEditText = findViewById(R.id.questionEditTextId);
        mPostAnswerButton.setEnabled(false);
        mPostAnswerButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // disables and darkens comment button
        mCommentButton.setEnabled(false);
        mCommentButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));


        // allows edit for question
        String questionString = mQuestionTextView.getText().toString();
        mQuestionTextView.setVisibility(View.GONE);
        mQuestionEditText.setVisibility(View.VISIBLE);
        mQuestionEditText.setText(questionString);

        // allows edit for question
//        String answerString = mQuestionAnswerTextView.getText().toString();
//        mQuestionAnswerTextView.setVisibility(View.GONE);
//        mQuestionAnswerEditText.setVisibility(View.VISIBLE);
//        mQuestionAnswerEditText.setText(answerString);
    }



    private void updateQuestion(){
        Question question = createQuestionObj();
        updateQuestionToCollection(question);
    }

    private Question createQuestionObj(){
        final Question question = new Question();
        return question;
    };

    private void updateQuestionToCollection(Question question) {
        final String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

        Object updatedQuestionText = mQuestionEditText.getText().toString();
        Object updatedAnswerText = mQuestionAnswerEditText.getText().toString();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = rootRef.collection("Questions");


        DocumentReference docRef = questionsRef.document(questionPathIDString); // <-- this works!****

        docRef.update("question", updatedQuestionText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Question text updated. it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
                Log.d(TAG, "onFailure: itemID " + questionPathIDString);
            }
        });

        docRef.update("answer", updatedAnswerText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Answer text updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of " + e.toString());
                Log.d(TAG, "onFailure: itemID" + questionPathIDString);
            }
        });


        Toast.makeText(this, "Question Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
