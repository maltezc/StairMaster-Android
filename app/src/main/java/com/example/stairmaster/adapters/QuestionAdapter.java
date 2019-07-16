package com.example.stairmaster.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stairmaster.QuestionProfileActivity2;
import com.example.stairmaster.R;
import com.example.stairmaster.models.Question;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

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
    protected void onBindViewHolder(@NonNull QuestionHolder questionHolder, int position, @NonNull Question model) {

        questionHolder.textViewQuestion.setText(model.getQuestion());
        questionHolder.textViewPriority.setText(String.valueOf(model.getQuestionPriority()));

    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item, viewGroup, false);
        return new QuestionHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
        notifyDataSetChanged();
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

//                        Intent intent = new Intent(mContext, QuestionProfileActivity.class);
                        Intent intent = new Intent(mContext, QuestionProfileActivity2.class);
                        intent.putExtra("question_string", textViewQuestion.getText());
                        intent.putExtra("questionAnswer_string", textViewAnswer.getText());
                        intent.putExtra("questionPriority_string", textViewPriority.getText());

                        String questionItemId = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("questionID_string", questionItemId);

                        String questionAuthorString = getSnapshots().getSnapshot(position).getString("questionAuthor");
                        intent.putExtra("questionAuthorString", questionAuthorString);


                        String questionTimestamp = getSnapshots().getSnapshot(position).getString("questionTimestamp");
                        intent.putExtra("questionTimestampString", questionTimestamp);

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
