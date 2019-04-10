package com.example.stairmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stairmaster.DashboardActivity;
import com.example.stairmaster.QuestionProfileActivity;
import com.example.stairmaster.R;
import com.example.stairmaster.models.Question;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class QuestionAdapter extends FirestoreRecyclerAdapter<Question, QuestionAdapter.QuestionHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QuestionAdapter(@NonNull FirestoreRecyclerOptions<Question> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuestionHolder holder, int position, @NonNull Question model) {

        holder.textViewQuestion.setText(model.getQuestion());
        holder.textViewAnswer.setText(model.getAnswer());
        holder.textViewPriority.setText(String.valueOf(model.getPriority()));

    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item, viewGroup, false);
        return new QuestionHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class QuestionHolder extends RecyclerView.ViewHolder{

        TextView textViewQuestion;
        TextView textViewAnswer;
        TextView textViewPriority;


        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_title);
            textViewAnswer = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            mContext = itemView.getContext();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String questionString = textViewQuestion.toString();

                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position, getSnapshots().getSnapshot(position).getId());

                        Intent intent = new Intent(mContext, QuestionProfileActivity.class);
                        intent.putExtra("question_string", textViewQuestion.getText());
                        intent.putExtra("questionAnswer_string", textViewAnswer.getText());
                        intent.putExtra("questionPriority_string", textViewPriority.getText());


                        String questionItemId = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("questionID_string", questionItemId);

                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position, String id);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }


}
