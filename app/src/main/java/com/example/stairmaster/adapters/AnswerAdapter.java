package com.example.stairmaster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

        answerHolder.answerItemTextView.setText(model.getAnswer());
        answerHolder.answerAuthorTextView.setText(model.getAuthor());
        answerHolder.answerTimeStampTextView.setText(model.getAnswerCreatedTimestamp());


        // TODO: 2019-08-25 check for firestore value. if answer. is checked, set state to show green, else checkmark is blank


        //another note: since question poser isnt the only one marking asnwer. figure out system to allow for "checked answer" to actually be true
//        answerHolder.answerCheckMark.



//        answerHolder.answerScoreTextView.setText(getSnapshots().getSnapshot(position).get("answerScore").toString());

        // grab user
        // set votemax = 1
        //if user has clicked up or downvote
            //keep arrow color gold



        answerHolder.mAnswerUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-07-26 check order of operations. seems to go through upvote and then also through downvote unchecking state
                if (answerHolder.mAnswerUpVoteButton.isChecked()){

                    answerUpVote(answerHolder, position, model);

                } else {
//                    answerDownVote(answerHolder, position, model);
                }
            }
        });


        answerHolder.mAnswerDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answerHolder.mAnswerDownVoteButton.isChecked()) {

                    answerDownVote(answerHolder, position, model);
                } else {

//                    answerUpVote(answerHolder, position, model);
                }
            }
        });


        final String answerFirebaseIdString = getSnapshots().getSnapshot(position).get("answerFirebaseId").toString();
        final CollectionReference answerCollectionRef = FirebaseFirestore.getInstance().collection("Answers");
        final DocumentReference answerDocRef = answerCollectionRef.document(answerFirebaseIdString);

        /*
        answerHolder.answerCheckMark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    answerHolder.answerCheckMark.setBackgroundResource(R.drawable.ic_check_green_24dp);

                }
//                answerDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.get("checked") == "true") {
//                            Log.d(TAG, "onSuccess: status is currently set to " + documentSnapshot.get("checked").toString());
//                        }
//                    }
//                });
            }
        });

         */



        answerDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("checked") == "true") {
//                    answerHolder.answerCheckMark.setBackgroundDrawable(R.drawable.ic_check_green_24dp);
//                    answerHolder.answerCheckMark.setBackground(R.drawable.ic_check_black_24dp);
//                    answerHolder.answerCheckMark.toggle();
//                    answerHolder.answerCheckMark.setButtonDrawable(R.drawable.ic_check_green_24dp);
                    answerHolder.answerCheckMark.setBackgroundResource(R.drawable.ic_check_green_24dp);
                }

            }
        });






        answerHolder.answerCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update answer isChecked to true

                final String answerFirebaseIdString = getSnapshots().getSnapshot(position).get("answerFirebaseId").toString();
                final CollectionReference answerCollectionRef = FirebaseFirestore.getInstance().collection("Answers");
                final DocumentReference answerDocRef = answerCollectionRef.document(answerFirebaseIdString);

                answerDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        boolean answerCheckedFalse = false;
                        String answerCheckedBoolean = documentSnapshot.get("checked").toString();
                        if (answerCheckedBoolean == "true") {
                            answerDocRef.update("checked", answerCheckedFalse).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(mContext, "answerCheckmark has been set to false.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
//                            updateAnswer2Checked(answerHolder, position, model);
                            // TODO: 2019-08-26 if answer checked is false, then set to true. otherwise set to false.
                            boolean answerCheckedTrue = true;
//                            answerHolder.answerCheckMark.setChecked(true);
//                            answerHolder.answerCheckMark.setActivated(true);
//                            answerHolder.answerCheckMark.toggle();
                            answerDocRef.update("checked", answerCheckedTrue).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(mContext, "answerCheckmark has been set to true.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
        ToggleButton answerCheckMark;
        TextView answerAuthorTextView;
        TextView answerTimeStampTextView;
        ToggleButton mAnswerUpVoteButton;
        ToggleButton mAnswerDownVoteButton;


        public AnswerHolder(@NonNull final View itemView) {
            super(itemView);
            answerItemTextView = itemView.findViewById(R.id.answerItemTextViewId);
            answerScoreTextView = itemView.findViewById(R.id.questionScoreId);
            answerCheckMark = itemView.findViewById(R.id.answerCheckMarkId);
            answerAuthorTextView = itemView.findViewById(R.id.answerAuthorTextViewId);
            answerTimeStampTextView = itemView.findViewById(R.id.answerTimeStampTextViewId);
            mAnswerUpVoteButton = itemView.findViewById(R.id.answerUpVoteId);
            mAnswerDownVoteButton = itemView.findViewById(R.id.answerDownVoteId);

            mContext = itemView.getContext();


        }



    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position, String id);


    }
    private void answerUpVote(@NonNull final AnswerAdapter.AnswerHolder answerHolder, final int position, @NonNull final Answer model) {
        final String answerScoreFBValue = getSnapshots().getSnapshot(position).get("answerScore").toString();
        final String answerFirebaseIdString = getSnapshots().getSnapshot(position).get("answerFirebaseId").toString();
//        Toast.makeText(mContext, "upvote button clicked " + answerScoreFBValue, Toast.LENGTH_SHORT).show();
        final CollectionReference answerCollectionRef = FirebaseFirestore.getInstance().collection("Answers");
        final DocumentReference answerDocRef = answerCollectionRef.document(answerFirebaseIdString);


        answerDocRef.update("answerScore", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            //                answerRef.document().update("answerscore", answerScoreTestInt).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: answerScore incremented");
//                answerHolder.answerScoreTextView.setText("testing");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: answerScore was not incremented", e);
            }
        });
    }
    private void answerDownVote(@NonNull final AnswerAdapter.AnswerHolder answerHolder, final int position, @NonNull final Answer model) {
        final String answerScoreFBValue = getSnapshots().getSnapshot(position).get("answerScore").toString();
        final String answerFirebaseIdString = getSnapshots().getSnapshot(position).get("answerFirebaseId").toString();
        final CollectionReference answerCollectionRef = FirebaseFirestore.getInstance().collection("Answers");
        final DocumentReference answerDocRef = answerCollectionRef.document(answerFirebaseIdString);
        Toast.makeText(mContext, "downvote button clicked " + answerScoreFBValue, Toast.LENGTH_SHORT).show();

        answerDocRef.update("answerScore", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            //                answerRef.document().update("answerscore", answerScoreTestInt).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: answerScore incremented");
                answerHolder.answerScoreTextView.setText("testing");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: answerScore was not incrememnted", e);
            }
        });

    }


}