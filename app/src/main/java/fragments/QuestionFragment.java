package fragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

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

        //firebase
        FirebaseAuth mAuth;
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        String questionPathIDString;


        EditText mQuestionEditText;
//        TextView mQuestionTextView;
        RecyclerView mAnswerRecyclerView;
        EditText mQuestionAnswerEditText;
//        TextView mQuestionPriorityContent;
//        TextView mQuestionAuthorTextView;
        TextView mDateTimeStampTextView;

        Button mPostAnswerButton;
        Button mCommentButton;
        Button mDeleteQuestionButton;
        RelativeLayout mCheckContainer, mBackArrowContainer;
        ImageButton mCheck;
        ImageButton mEditButton;
        ImageButton mSaveButton;
        ImageButton mQuestionUpVoteButton;
        ImageButton mQuestionDownVoteButton;

//        mQuestionTextView = getView().findViewById(R.id.questionTextViewId);
        mQuestionAnswerEditText = getActivity().findViewById(R.id.answerEditTextId);
//        mQuestionPriorityContent = getActivity().findViewById(R.id.starTextViewId);
        mCheckContainer = getActivity().findViewById(R.id.check_container);
        mBackArrowContainer = getActivity().findViewById(R.id.back_arrow_container);
        mCheck = getActivity().findViewById(R.id.toolbar_check);
        mEditButton = getActivity().findViewById(R.id.edit_question);
        mSaveButton = getActivity().findViewById(R.id.save_question);
        mPostAnswerButton = getActivity().findViewById(R.id.postAnswerButtonId);
        mCommentButton = getActivity().findViewById(R.id.commentButtonId);
        mDeleteQuestionButton = getActivity().findViewById(R.id.deleteQuestionButtonId);
//        mQuestionAuthorTextView = getActivity().findViewById(R.id.questionAuthorTextViewId);
        mDateTimeStampTextView = getActivity().findViewById(R.id.dateTimeStampTextViewId);
        mQuestionUpVoteButton = getActivity().findViewById(R.id.answerUpVoteId);
        mQuestionDownVoteButton = getActivity().findViewById(R.id.answerDownVoteId);

        getIncomingIntent();

        // set question score to firebase score


        mQuestionUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionUpVote();
            }
        });

        mQuestionDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionDownVote();
            }
        });

    }

    private void questionUpVote() {
        Log.d(TAG, "questionUpVote: clicked");
        // update UI score
        // update firebase score

    }

    private void questionDownVote() {
        Log.d(TAG, "questionDownVote: clicked");
        // update UI score
        // update firebase score
    }

    public void getIncomingIntent() {

        Log.d(TAG, "getIncomingIntent: checking for incoming intent");
        TextView mQuestionTextView = getView().findViewById(R.id.questionTextViewId);
        TextView mDateTimeStampTextView = getActivity().findViewById(R.id.dateTimeStampTextViewId);
        TextView mQuestionAuthorTextView = getActivity().findViewById(R.id.questionAuthorTextViewId);
        TextView mQuestionPriorityContent = getActivity().findViewById(R.id.starTextViewId);



        if (getActivity().getIntent().hasExtra("question_string")) {

            Log.d(TAG, "getIncomingIntent: " + mQuestionTextView.toString());
            String questionString = (String) getActivity().getIntent().getExtras().get("question_string");
//            String questionAnswerString = (String) getIntent().getExtras().get("questionAnswer_string");
            String questionPriorityString = (String) getActivity().getIntent().getExtras().get("questionPriority_string");
            String questionPathIDString = (String) getActivity().getIntent().getExtras().get("questionID_string");
            String questionAuthorString = (String) getActivity().getIntent().getExtras().get("questionAuthorString");
            String questionDateTimeStamp = (String) getActivity().getIntent().getExtras().get("questionTimestampString");

            mDateTimeStampTextView.setText(questionDateTimeStamp);
            mQuestionAuthorTextView.setText(questionAuthorString);
            mQuestionTextView.setText(questionString);
            mQuestionPriorityContent.setText(questionPriorityString);


            System.out.println(questionPathIDString);
        }
    }



}
