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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class AnswerAdapter extends FirestoreRecyclerAdapter<Answer, AnswerAdapter.AnswerHolder> {


//    public AnswerAdapater onClickListener;
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
    protected void onBindViewHolder(@NonNull final AnswerAdapter.AnswerHolder answerHolder, final int position, @NonNull final Answer model) {

//        final AnswerHolder databaseReference = getItem(position);
//        final AnswerHolder databaseReference = getItem(position);
//        position = getItem(position);
//        position = getAdapterPosition();

        answerHolder.answerItemTextView.setText(model.getAnswer());
        answerHolder.answerAuthorTextView.setText(model.getAuthor());
        answerHolder.answerTimeStampTextView.setText(model.getTimestamp());
//        answerHolder.answerScoreTextView.setText(String.valueOf(model.getAnswerScore()));
        answerHolder.answerScoreTextView.setText(getSnapshots().getSnapshot(position).get("answerScore").toString());

//        answerHolder.answerItemTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        String answerScoreFBValue2 = getSnapshots().getSnapshot(position).getString("answerScore");



        answerHolder.mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String answerScoreFBValue = getSnapshots().getSnapshot(position).get("answerScore").toString();
                final String answerFirebaseIdString = getSnapshots().getSnapshot(position).get("answerFirebaseId").toString();
                Toast.makeText(mContext, "upvote button clicked " + answerScoreFBValue, Toast.LENGTH_SHORT).show();


                final CollectionReference answerCollectionRef = FirebaseFirestore.getInstance().collection("Answers");
                final DocumentReference answerDocRef = answerCollectionRef.document(answerFirebaseIdString);

                answerDocRef.update("answerScore", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                answerRef.document().update("answerscore", answerScoreTestInt).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: answerScore incremented");
//                        answerDocRef.get("answerScore");
                        answerHolder.answerScoreTextView.setText("testing");
//                        answerHolder.answerScoreTextView.setText(answerScoreFBValue);
//                        answerHolder.answerScoreTextView.seText(model.getAnswerScore()); // TODO: 2019-07-18 how to update score on screen! almost there!
//                        answerHolder.answerScoreTextView.setText(String.valueOf(model.getAnswerScore()));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: answerScore was not incrememnted", e);

                    }
                });

            }
        });

    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        notifyDataSetChanged();
    }

    public class AnswerHolder extends RecyclerView.ViewHolder{


        TextView answerItemTextView;

        TextView answerScoreTextView;
        ImageButton answerCheckMark;
        TextView answerAuthorTextView;
        TextView answerTimeStampTextView;
        ImageButton mAnswerUpVoteButton;
        ImageButton mAnswerDownVoteButton;


        public AnswerHolder(@NonNull final View itemView) {
            super(itemView);
            answerItemTextView = itemView.findViewById(R.id.answerItemTextViewId);
            answerScoreTextView = itemView.findViewById(R.id.answerScoreId);
            answerCheckMark = itemView.findViewById(R.id.answerCheckMarkId);
            answerAuthorTextView = itemView.findViewById(R.id.answerAuthorTextViewId);
            answerTimeStampTextView = itemView.findViewById(R.id.answerTimeStampTextViewId);
            mAnswerUpVoteButton = itemView.findViewById(R.id.answerUpVoteId);
            mAnswerDownVoteButton = itemView.findViewById(R.id.answerDownVoteId);


            mContext = itemView.getContext();



//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    final int position = getAdapterPosition();
//
//
//                    if (position != RecyclerView.NO_POSITION && listener != null) {
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position, getSnapshots().getSnapshot(position).getId());
//
//                        final String answerAuthorString = getSnapshots().getSnapshot(position).getString("author");
////                        String questionTimestamp = getSnapshots().getSnapshot(position).getString("questionTimestamp");


//                        //TODO: link to answer's firebaseid so you can upvote that one single answer.
//                        //TODO: do same for questions for upvote/downvote functionality
//                        mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(itemView.getContext(), "Answer upvote Button clicked" + answerAuthorString, Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "onClick: Answer UpVote Button clicked");
//
//                            }
//                        });
//
//                        mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Log.d(TAG, "onClick: Answer DownVote Button Clicked");
//                                Toast.makeText(itemView.getContext(), "Answer DownVote Button Clicked" + getSnapshots().getSnapshot(position).getId(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                     }


//                    mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(itemView.getContext(), "Answer upvote Button clicked from inside of listener" + getItemId(), Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onClick: Answer UpVote Button clicked from inside of listener");
//                        }
//                    });
//
//                    mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(itemView.getContext(), "Answer DownVote Button Clicked from inside of listener" + getSnapshots().getSnapshot(position).getId(), Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onClick: Answer DownVote Button Clicked from inside of listener");
//                        }
//                    });


//                };
//            });

            /**
            mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Answer upvote Button clicked from outside of listener" + getItemId(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Answer UpVote Button clicked from outside of listener");
//                    itemView.getContext().
                }
            });

            mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Answer DownVote Button Clicked from outside of listener" + getItemId(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Answer DownVote Button Clicked from outside of listener");
                }
            });
            **/
        }

    }
    public interface OnItemClickListener {

        void onItemClick(DocumentSnapshot documentSnapshot, int position, String id);



    }





//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.listener = listener;
//
//    }

//    public interface AnswerAdapater

//    public void setOnItemClickListener(AnswerAdapter.OnItemClickListener listener) {
//        this.listener = listener;
//    }

    // Model below
//    public void setOnItemClickListener(QuestionAdapter.OnItemClickListener listener) {
//        this.listener = listener;
//    }
}