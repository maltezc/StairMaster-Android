package com.example.stairmaster.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.R;
import com.example.stairmaster.models.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AnswerAdapter extends FirestoreRecyclerAdapter<Answer, AnswerAdapter.AnswerHolder> {


    private Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AnswerAdapter(@NonNull FirestoreRecyclerOptions<Answer> options) {

       super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnswerAdapter.AnswerHolder answerHolder, int position, @NonNull Answer model) {

        answerHolder.answerItemTextView.setText(model.getAnswer());
    }


    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class AnswerHolder extends RecyclerView.ViewHolder{

        TextView answerItemTextView;
        TextView answerScoreId;
        ImageButton answerCheckMark;
        TextView answerAuthorTextView;
        TextView answerTimeStampTextView;


        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            answerItemTextView = itemView.findViewById(R.id.answerItemTextViewId);
            answerScoreId = itemView.findViewById(R.id.answerScoreId);
            answerCheckMark = itemView.findViewById(R.id.answerCheckMarkId);
            answerAuthorTextView = itemView.findViewById(R.id.answerAuthorTextViewId);
            answerTimeStampTextView = itemView.findViewById(R.id.answerTimeStampTextViewId);

            mContext = itemView.getContext();


            
        }
    }
}
