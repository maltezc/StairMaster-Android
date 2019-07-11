package com.example.stairmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fragments.AnswersFragment.OnListFragmentInteractionListener;
import com.example.stairmaster.dummy.DummyContent.DummyItem;
import com.example.stairmaster.models.Answer;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAnswersRecyclerViewAdapter extends RecyclerView.Adapter<MyAnswersRecyclerViewAdapter.ViewHolder> {

    private final List<Answer> mAnswers;
//    private final List<DummyItem> mAnswers;
    private final OnListFragmentInteractionListener mListener;

    public MyAnswersRecyclerViewAdapter(List<Answer> answers, OnListFragmentInteractionListener listener) {
        mAnswers = answers;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder answerHolder, int position) {
//    public void onBindViewHolder(final ViewHolder holder, int position) {
        answerHolder.mAnswer = mAnswers.get(position);
//        holder.mIdView.setText(mAnswers.get(position).id);
//        holder.mContentView.setText(mAnswers.get(position).content);

//        TODO: not sure how to make this work below. try to reverse engineer from dummy item content/stackO
//        answerHolder.answerItemTextView.setText(model.getAnswer());
//        answerHolder.answerAuthorTextView.setText(model.getAuthor());
//        answerHolder.answerTimeStampTextView.setText(model.getTimestamp());
//        answerHolder.answerScoreId.setText(String.valueOf(model.getScore()));

        answerHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(answerHolder.mAnswer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Answer mAnswer;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
