package com.example.stairmaster;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import com.example.stairmaster.models.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class QuestionProfileActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

    private static final String TAG = "QuestionProfileActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;



    // ui components
    private EditText mQuestionEditText;
    private TextView mQuestionTextView;
    private TextView mQuestionAnswerContent;
    private TextView mQuestionPriorityContent;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck;
    private ImageButton mBackArrow;
//    private ImageButton mSaveButton;
    private ImageButton mEditButton;
    private Button mPostAnswerButton;
    private Button mCommentButton;
    private ImageButton mSaveButton;


    //vars
    private GestureDetector mGestureDetector;
    private boolean mShowSaveIcon;

    private FirebaseFirestore firestoreDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile);

        firestoreDB = FirebaseFirestore.getInstance();

        setTitle("Question");

        mQuestionTextView = findViewById(R.id.questionTextViewID);
        mQuestionAnswerContent = findViewById(R.id.answerTextViewID);
        mQuestionPriorityContent = findViewById(R.id.starTextViewID);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mEditButton = findViewById(R.id.edit_question);
        mSaveButton = findViewById(R.id.save_question);
        mPostAnswerButton = findViewById(R.id.postAnswerButtonID);
        mCommentButton = findViewById(R.id.commentButtonID);


        getIncomingIntent();
    }





    private void setListeners(){
//        mAnswer.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
//        mQuestionViewTitle.setOnClickListener(this);
//        mCheck.setOnClickListener(this);
//        mBackArrow.setOnClickListener(this);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");

        if (getIntent().hasExtra("question_string")) {

            Log.d(TAG, "getIncomingIntent: " + mQuestionTextView.toString());
            String questionString = (String) getIntent().getExtras().get("question_string");
            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
            String questionPriorityString = (String) getIntent().getExtras().get("questionPriority_string");
            String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

            System.out.println(questionPathIDString);

            mQuestionTextView.setText(questionString);
            mQuestionAnswerContent.setText(questionAnswerString);
            mQuestionPriorityContent.setText(questionPriorityString);

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

        mQuestionEditText = findViewById(R.id.questionEditTextID);
        mPostAnswerButton.setEnabled(false);
        mPostAnswerButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        mCommentButton.setEnabled(false);
        mCommentButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        String questionString = mQuestionTextView.getText().toString();
        mQuestionTextView.setVisibility(View.GONE);
        mQuestionEditText.setVisibility(View.VISIBLE);
        mQuestionEditText.setText(questionString);
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

        Object updatedText = mQuestionEditText.getText().toString();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = rootRef.collection("Questions");

        DocumentReference docRef = questionsRef.document(questionPathIDString); // <-- this works!****

        docRef.update("question", updatedText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
                Log.d(TAG, "onFailure: itemID " + questionPathIDString);
            }
        });


        Toast.makeText(this, "Question Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
