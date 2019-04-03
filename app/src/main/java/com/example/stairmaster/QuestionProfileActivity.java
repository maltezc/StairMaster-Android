package com.example.stairmaster;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stairmaster.models.Question;

public class QuestionProfileActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

    Button backButton;
    String answerString;
    private static final String TAG = "QuestionProfileActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // ui components
    private TextView mQuestionViewTitle;
    private EditText mQuestionEditTitle;
    private EditText mQuestionContent;
    private TextView mQuestionAnswerContent;
    private TextView mQuestionPriorityContent;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck;
    private ImageButton mBackArrow;

    //vars
    private boolean mIsNewNote;
    private Question mInitialNote; // will be changed to Question instead of Question
    private GestureDetector mGestureDetector;
    private int mMode;
    private Question mNoteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile);

//        mQuestionViewTitle = findViewById(R.id.note_text_title);
//        mQuestionEditTitle = findViewById(R.id.note_edit_title);
        mQuestionContent = findViewById(R.id.questionEditTextID);
        mQuestionAnswerContent = findViewById(R.id.answerEditTextID);
        mQuestionPriorityContent = findViewById(R.id.starTextViewID);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
//        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        backButton = (Button)findViewById(R.id.backButton);

//        Toolbar toolbar = (Toolbar)findViewById(R.id.mainToolbar);
//        setSupportActionBar(toolbar);

//        if (getIncomingIntent()) {
//            // this is a new note, (EDIT MODE)
//            setNewNoteProperties();
////            enableEditMode();
//
//        } else {
//            // this is NOT a new note (View mode)
//            setNoteProperties();
////            disableContentInteraction();
//        }
//        setListeners();

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

            Log.d(TAG, "getIncomingIntent: " + mQuestionContent.toString());
//            String questionString = getIntent().getStringExtra("question_string");
            String questionString = (String) getIntent().getExtras().get("question_string");
            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
            String questionPriorityString = (String) getIntent().getExtras().get("questionPriority_string");


            mQuestionContent.setText(questionString);
            mQuestionAnswerContent.setText(questionAnswerString);
            mQuestionPriorityContent.setText(questionPriorityString);

        }

    }

    private void setQuestionContent() {
        EditText questionEditText = findViewById(R.id.questionEditTextID);
        questionEditText.setText(mQuestionContent.toString());


    }


//        if(getIntent().hasExtra("selected_note")) {
//            mInitialNote = getIntent().getParcelableExtra("selected_note");
//            mNoteFinal = getIntent().getParcelableExtra("selected_note");
//            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());
//
//            mMode = EDIT_MODE_DISABLED;
//            mIsNewNote = false;
//            return false;
//        }
//        mMode = EDIT_MODE_ENABLED;
//        mIsNewNote = true;
//        return true;
//    };


//    private void disableContentInteraction() {
//        mAnswer.setKeyListener(null);
//        mAnswer.setFocusable(false);
//        mAnswer.setFocusableInTouchMode(false);
//        mAnswer.setCursorVisible(false);
//        mAnswer.clearFocus();
//    }
//
//    private void enableContentInteraction() {
//        mAnswer.setKeyListener(new EditText(this).getKeyListener());
//        mAnswer.setFocusable(true);
//        mAnswer.setFocusableInTouchMode(true);
//        mAnswer.setCursorVisible(true);
//        mAnswer.requestFocus();
//
//
//    }


//    private void enableEditMode(){
//        mBackArrowContainer.setVisibility(View.GONE);
//        mCheckContainer.setVisibility(View.VISIBLE);
//
//        mQuestionViewTitle.setVisibility(View.GONE);
//        mQuestionEditTitle.setVisibility(View.VISIBLE);
//
//        mMode = EDIT_MODE_ENABLED;
//
//        enableContentInteraction();
//    }

//    private void disableEditMode(){
//        mBackArrowContainer.setVisibility(View.VISIBLE);
//        mCheckContainer.setVisibility(View.GONE);
//
//        mQuestionViewTitle.setVisibility(View.VISIBLE);
//        mQuestionEditTitle.setVisibility(View.GONE);
//
//        mMode = EDIT_MODE_DISABLED;
//
//        disableContentInteraction();
//
//        String temp = mAnswer.getText().toString();
//        temp = temp.replace("\n", "");
//        temp = temp.replace(" ", "");
//        if (temp.length() > 0) {
////            mNoteFinal.setTitle(mQuestionEditTitle.getText().toString());
//            mNoteFinal.setAnswer(mQuestionContent.getText().toString());
//            String timestamp = "Jan 2019";
////            mNoteFinal.setTimestamp(timestamp);
////            if (!mNoteFinal.getAnswer().equals(mInitialNote.getAnswer());
////            || !mNoteFinal.getQuestion().equals(mInitialNote.getQuestion())) {
//////                saveChanges();
//            }
//        }
//
//    }

    private void hideSoftKeyboard() {
        //keyboard in android is referred to as the Soft Keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }



    private void setNoteProperties() {
//            mViewTitle.setText(mInitialNote.getTitle());
        mQuestionViewTitle.setText(mInitialNote.getQuestion());
        mQuestionEditTitle.setText(mInitialNote.getQuestion());
        mQuestionContent.setText(mInitialNote.getQuestion());
        mQuestionAnswerContent.setText(mInitialNote.getAnswer());
    }


    private void setNewNoteProperties() {
//        mQuestionViewTitle.setText("Question Title");
//            mViewTitle.setText("Question Title");
//        mQuestionEditTitle.setText("Question2 Title");

        mInitialNote = new Question();
        mNoteFinal = new Question();
//        mInitialNote.setQuestion("Question Title");
//        mNoteFinal.setQuestion("Question Title");
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
//                enableEditMode();
                mQuestionEditTitle.requestFocus();
                mQuestionEditTitle.setSelection(mQuestionEditTitle.length());
                break;
            }

//            case R.id.toolbar_back_arrow: {
//                finish(); // can only call finish in an activity. cant call in a fragment. it wont work
//                break;
//            }


        }
    }


    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();

        }
    }
}
