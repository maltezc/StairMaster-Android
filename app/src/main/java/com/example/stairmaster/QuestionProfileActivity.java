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
    private boolean mIsNewNote;
    private Question mInitialNote; // will be changed to Question instead of Question
    private GestureDetector mGestureDetector;
    private int mMode;
    private Question mNoteFinal;
    private boolean mShowSaveIcon;

    private FirebaseFirestore firestoreDB;
    private String docId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile);

        firestoreDB = FirebaseFirestore.getInstance();

        Question question = null;


        setTitle("Question");

//        mQuestionViewTitle = findViewById(R.id.note_text_title);
//        mQuestionEditTitle = findViewById(R.id.note_edit_title);
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

//        mEditButton.setVisibility(View.GONE);


//        mEditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
//
//                mQuestionEditText = findViewById(R.id.questionEditTextID);
//
//
//                mPostAnswerButton.setEnabled(false);
//                mPostAnswerButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//
//                mCommentButton.setEnabled(false);
//                mCommentButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//
//                String questionString = mQuestionTextView.getText().toString();
//                mQuestionTextView.setVisibility(View.GONE);
//                mQuestionEditText.setVisibility(View.VISIBLE);
//                mQuestionEditText.setText(questionString);
//                mSaveButton.setVisibility(View.VISIBLE);
//                mEditButton.setVisibility(View.GONE);
//
//                mSaveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        updateQuestion();
//
//                    }
//                });
//
//
//                    //todo: set menu to hide edit button and show save button
//
//
//            }
//        });


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

            Log.d(TAG, "getIncomingIntent: " + mQuestionTextView.toString());
//            String questionString = getIntent().getStringExtra("question_string");
            String questionString = (String) getIntent().getExtras().get("question_string");
            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
            String questionPriorityString = (String) getIntent().getExtras().get("questionPriority_string");
            String questionPath = (String) getIntent().getExtras().get("questionPath_string");

            mQuestionTextView.setText(questionString);
            mQuestionAnswerContent.setText(questionAnswerString);
            mQuestionPriorityContent.setText(questionPriorityString);

        }
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
//            mNoteFinal.setAnswer(mQuestionTextView.getText().toString());
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
//                mQuestionEditTitle.requestFocus();
//                mQuestionEditTitle.setSelection(mQuestionEditTitle.length());
                break;
            }

//            case R.id.toolbar_back_arrow: {
//                finish(); // can only call finish in an activity. cant call in a fragment. it wont work
//                break;
//            }


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

//    private void showEventScreen() {
//        Intent i = new Intent();
//        i.setClass(getActivity(), EventActivity.class);
//        startActivity(i);
//    }

    private void updateQuestionToCollection(Question question) {
        String questionString = mQuestionTextView.getText().toString();
        Object updatedText = mQuestionEditText.getText().toString();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = rootRef.collection("Questions");
        final String docId = rootRef.collection("Questions").document().getId();



        final String questionPath = rootRef.collection("Questions").document().getPath();
//        DocumentReference docRef = questionsRef.document("yCCRgoRIAmtmKmTFLYJX");
//        DocumentReference docRef = questionsRef.document("FUaCgO8CmTLDYpECsnzK"); // <-- this works!****
        DocumentReference docRef = questionsRef.document(docId);
        docRef.update("question", updatedText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
                Log.d(TAG, "onFailure: itemID " + questionPath);
            }
        });

//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        CollectionReference questionsRef = rootRef.collection("Questions");
//        String docId = rootRef.collection("Questions").document().getId();
//
//        String questionPath = rootRef.collection("Questions").document(docId).collection("question").getPath();
//        DocumentReference docRef = questionsRef.document(questionPath);
//        docRef.update("question", updatedText).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: it worked");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
//            }
//        });


//        String docId = rootRef.collection("Questions").document().getId();
//        System.out.println(docId);
//        String questionPath = rootRef.collection("Questions").document(docId).collection("question").getPath();
//        String questionPath2 = rootRef.collection("Questions").document(docId).getPath();

//        System.out.println(questionPath);
//        rootRef.collection(questionPath).document(docId).update("question", updatedText).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: it worked");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
//            }
//        });
//        System.out.println(rootRef.collection(questionPath).document(docId).update("question", updatedText));


//        rootRef.collection(questionPath).document(docId).set("questions", SetOptions.merge())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: shit was successful");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: sht was not successful");
//            }
//        });



//        String questionPath2 = rootRef.collection("Questions").document().getPath();

//        DocumentSnapshot documentSnapshot = qRef.get("question").

//        DocumentReference ref = rootRef.collection("Questions").document(questionPath);
//        CollectionReference ref = rootRef.collection("Questions").document().collection("question");
//        CollectionReference ref = rootRef.collection("Questions").document().collection(questionPath);

//        Toast.makeText(this, "this is the path: " + questionPath, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "updateQuestion: " + ref.get(question.getAnswer));
//        Log.d(TAG, "updateQuestion: " + ref.collection(questionPath).document("question", questionPath).toString())
//        Log.d(TAG, "updateQuestion: " + ref.document(questionPath)("question").toString());
//        Log.d(TAG, "updateQuestion: " + ref.document(questionPath).get(question).toString());
//        ref.collection("question")
//        db.child("Spacecraft").push().setValue(spacecraft);
//        rootRef.collection("Questions").document().update("question", updatedText);
//        rootRef.collection("Questions").document(questionPath).update("question", updatedText);

//        TextView savedText = mQuestionTextView.setText(updatedText);

//        DocumentReference documentReference = rootRef.collection("Questions").document(questionPath).update("question", );
//        DocumentReference documentReference = rootRef.document(questionPath).update("question", updatedText);
//        CollectionReference collectionReference = rootRef.collection(questionPath).document().update("question", updatedText);
//        CollectionReference collectionReference1 = rootRef.collection(questionPath).document(questionPath).update("questions", updatedText);
//        rootRef.collection("events")
//                .document()
//                .update(event, SetOptions.merge())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                    }
//                })




//






//        String path = rootRef.collection("Questions").get;
//        String path = rootRef.collection("Questions").getPath(questionContext);

//        CollectionReference questionRef = FirebaseFirestore.getInstance()
//                .collection("Questions");
////        questionRef.add(new Question())

//        questionRef.add(new Question(questionString);
//        questionRef.

        Toast.makeText(this, "Question Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
