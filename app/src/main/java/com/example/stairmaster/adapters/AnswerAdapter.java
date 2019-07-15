package com.example.stairmaster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.R;
import com.example.stairmaster.models.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class AnswerAdapter extends FirestoreRecyclerAdapter<Answer, AnswerAdapter.AnswerHolder> {

    private QuestionAdapter.OnItemClickListener listener;
    private Context mContext;
    private static final String TAG = "AnswerAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AnswerAdapter(@NonNull FirestoreRecyclerOptions<Answer> options) {

        super(options);
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_item, viewGroup, false);
        return new AnswerHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnswerAdapter.AnswerHolder answerHolder, int position, @NonNull Answer model) {

//        final AnswerHolder databaseReference = getItem(position);
//        final AnswerHolder databaseReference = getItem(position);
//        position = getItem(position);
//        position = getAdapterPosition();

        answerHolder.answerItemTextView.setText(model.getAnswer());
        answerHolder.answerAuthorTextView.setText(model.getAuthor());
        answerHolder.answerTimeStampTextView.setText(model.getTimestamp());
        answerHolder.answerScoreId.setText(String.valueOf(model.getScore()));

//        answerHolder.answerItemTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        notifyDataSetChanged();
    }
    public class AnswerHolder extends RecyclerView.ViewHolder{


        TextView answerItemTextView;
        TextView answerScoreId;
        ImageButton answerCheckMark;
        TextView answerAuthorTextView;
        TextView answerTimeStampTextView;
        ImageButton mAnswerUpVoteButton;
        ImageButton mAnswerDownVoteButton;



        public AnswerHolder(@NonNull final View itemView) {
            super(itemView);
            answerItemTextView = itemView.findViewById(R.id.answerItemTextViewId);
            answerScoreId = itemView.findViewById(R.id.answerScoreId);
            answerCheckMark = itemView.findViewById(R.id.answerCheckMarkId);
            answerAuthorTextView = itemView.findViewById(R.id.answerAuthorTextViewId);
            answerTimeStampTextView = itemView.findViewById(R.id.answerTimeStampTextViewId);
            mAnswerUpVoteButton = itemView.findViewById(R.id.answerUpVoteId);
            mAnswerDownVoteButton = itemView.findViewById(R.id.answerDownVoteId);


            mContext = itemView.getContext();


            //TODO: link to answer's firebaseid so you can upvote that one single answer.
            //TODO: do same for questions for upvote/downvote functionality
            mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Answer upvote Button clicked" + getItemId(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Answer UpVote Button clicked");
                }
            });

            mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Answer DownVote Button Clicked");
                    Toast.makeText(itemView.getContext(), "Answer DownVote Button Clicked" + getSnapshots().get(), Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
}