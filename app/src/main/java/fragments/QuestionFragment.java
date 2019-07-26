package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.NewAnswerActivity;
import com.example.stairmaster.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        EditText mQuestionEditText;
//        TextView mQuestionTextView;
        final RecyclerView mAnswerRecyclerView;
        EditText mQuestionAnswerEditText;
        TextView mQuestionPriorityContent;
//        TextView mQuestionAuthorTextView;
        TextView mDateTimeStampTextView;
        TextView mQuestionScoreIdTextView;

        Button mPostAnswerButton;
        Button mCommentButton;
        Button mDeleteQuestionButton;
        Button mQuestionProfEditButton;
        final ToggleButton mQuestionUpVoteButton;
        final ToggleButton mQuestionDownVoteButton;
        RelativeLayout mCheckContainer, mBackArrowContainer;
        ImageButton mCheck;
        ImageButton mEditButton;
        ImageButton mSaveButton;

        mQuestionAnswerEditText = getActivity().findViewById(R.id.answerEditTextId);
        mQuestionPriorityContent = getActivity().findViewById(R.id.starTextViewId);
//        mCheckContainer = getActivity().findViewById(R.id.check_container);
//        mBackArrowContainer = getActivity().findViewById(R.id.back_arrow_container);
//        mCheck = getActivity().findViewById(R.id.toolbar_check);
        mEditButton = getActivity().findViewById(R.id.edit_question);
        mQuestionProfEditButton = getActivity().findViewById(R.id.questionEditButtonId);
        mSaveButton = getActivity().findViewById(R.id.save_question);
        mPostAnswerButton = getActivity().findViewById(R.id.postAnswerButtonId);
        mCommentButton = getActivity().findViewById(R.id.commentButtonId);
        mDeleteQuestionButton = getActivity().findViewById(R.id.deleteQuestionButtonId);
//        mQuestionAuthorTextView = getActivity().findViewById(R.id.questionAuthorTextViewId);
        mDateTimeStampTextView = getActivity().findViewById(R.id.dateTimeStampTextViewId);
        mQuestionUpVoteButton = getActivity().findViewById(R.id.questionUpVoteId);
        mQuestionDownVoteButton = getActivity().findViewById(R.id.questionDownVoteId);
        mQuestionScoreIdTextView = getActivity().findViewById(R.id.questionScoreId);

        getIncomingIntent();

        // set question score to firebase score
        String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionColRef = firestoreDB.collection("Questions");
        DocumentReference questionDocRef = questionColRef.document(questionPathIDString);
        mQuestionScoreIdTextView = getActivity().findViewById(R.id.questionScoreId);
//


        mQuestionUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestionUpVoteButton.isChecked()) {
                    questionUpVote();
                } else {
                    questionDownVote();
                }
            }
        });

        mQuestionDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionDownVoteButton.isChecked()) {
                    questionDownVote();
                } else {
                    questionUpVote();
                }
            }
        });

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Comment Button clicked");
                Toast.makeText(getContext(), "Comment Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mQuestionProfEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Edit Button Clicked");
                Toast.makeText(getContext(), "Edit Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mDeleteQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Delete Button Clicked");
                Toast.makeText(getContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mPostAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Answer Button Clicked");
                Toast.makeText(getContext(), "Post Answer Button Clicked", Toast.LENGTH_SHORT).show();
                goToNewAnswerActivity();
            }
        });

    }

    private void goToNewAnswerActivity() {

        getIncomingIntent();

        String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
        TextView mQuestionTextView = getView().findViewById(R.id.questionTextViewId);
        TextView mQuestionAuthorTextView = getActivity().findViewById(R.id.questionAuthorTextViewId);


        Intent intent = new Intent(getContext(), NewAnswerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("question_string", mQuestionTextView.getText());
        intent.putExtra("questionAuthor_string", mQuestionAuthorTextView.getText());
        intent.putExtra("questionID_string", questionPathIDString);

        startActivity(intent);

    }

    private void questionUpVote() {
        Log.d(TAG, "questionUpVote: clicked");
        Toast.makeText(getContext(), "Question Up Vote Clicked", Toast.LENGTH_SHORT).show();


        String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
        FirebaseAuth mAuth;
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionColRef = firestoreDB.collection("Questions");
        DocumentReference questionDocRef = questionColRef.document(questionPathIDString);



        questionDocRef.update("questionScore", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: question was upvoted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: question was not upvoted");
            }
        });

        questionDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String mFirebaseScoreString = documentSnapshot.get("questionScore").toString();
//                Log.d(TAG, "onSuccess: question score is ");
                TextView mQuestionScore = getActivity().findViewById(R.id.questionScoreId);
                mQuestionScore.setText(mFirebaseScoreString);
            }
        });


    }

    private void questionDownVote() {
        Log.d(TAG, "questionDownVote: clicked");
        Toast.makeText(getContext(), "Question DownVote Clicked", Toast.LENGTH_SHORT).show();
        // update UI score
        // update firebase score
        String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
        FirebaseAuth mAuth;
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionColRef = firestoreDB.collection("Questions");
        DocumentReference questionDocRef = questionColRef.document(questionPathIDString);


        questionDocRef.update("questionScore", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: question was downvoted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: question was not downvoted");
            }
        });

        questionDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String mFirebaseScoreString = documentSnapshot.get("questionScore").toString();
//                Log.d(TAG, "onSuccess: question score is ");
                TextView mQuestionScore = getActivity().findViewById(R.id.questionScoreId);
                mQuestionScore.setText(mFirebaseScoreString);
            }
        });
    }

    public void getIncomingIntent() {

        Log.d(TAG, "getIncomingIntent: checking for incoming intent");
        TextView mQuestionTextView = getView().findViewById(R.id.questionTextViewId);
        TextView mDateTimeStampTextView = getActivity().findViewById(R.id.dateTimeStampTextViewId);
        TextView mQuestionAuthorTextView = getActivity().findViewById(R.id.questionAuthorTextViewId);
        TextView mQuestionPriorityContent = getActivity().findViewById(R.id.starTextViewId);
        TextView mQuestionScoreContent = getActivity().findViewById(R.id.questionScoreId);

        if (getActivity().getIntent().hasExtra("question_string")) {

            Log.d(TAG, "getIncomingIntent: " + mQuestionTextView.toString());
            String questionString = (String) getActivity().getIntent().getExtras().get("question_string");
//            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
//            String questionPriorityString = (String) getActivity().getIntent().getExtras().get("questionScore_string");
            String questionScoreString = (String) getActivity().getIntent().getExtras().get("questionScore_string");
            String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
            String questionAuthorString = (String) getActivity().getIntent().getExtras().get("questionAuthorString");
            String questionDateTimeStamp = (String) getActivity().getIntent().getExtras().get("questionTimestampString");

            mDateTimeStampTextView.setText(questionDateTimeStamp);
            mQuestionAuthorTextView.setText(questionAuthorString);
            mQuestionTextView.setText(questionString);
//            mQuestionPriorityContent.setText(questionPriorityString);
            mQuestionScoreContent.setText(questionScoreString);

            System.out.println(questionPathIDString);
        }
    }



}
