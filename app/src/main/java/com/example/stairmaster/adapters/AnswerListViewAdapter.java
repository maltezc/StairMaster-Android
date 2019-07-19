package com.example.stairmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stairmaster.R;
import com.example.stairmaster.models.Answer;

public class AnswerListViewAdapter extends ArrayAdapter<Answer> {
    public AnswerListViewAdapter(@NonNull Context context, int resource) {
//    public AnswerListViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.answer_item,parent,false);
        }

        TextView answerItemTextView = (TextView) convertView.findViewById(R.id.answerItemTextViewId);
        TextView answerAuthorTextView = (TextView) convertView.findViewById(R.id.answerAuthorTextViewId);
        TextView answerTimeStampTextView = (TextView) convertView.findViewById(R.id.answerTimeStampTextViewId);
        TextView answerScore = (TextView) convertView.findViewById(R.id.answerScoreId);

        Answer answer = getItem(position);

        answerItemTextView.setText(answer.getAnswer());
        answerAuthorTextView.setText(answer.getAuthor());
        answerTimeStampTextView.setText(answer.getTimestamp());
        answerScore.setText(answer.getAnswerScore());

//        answerHolder.answerItemTextView.setText(model.getAnswer());
//        answerHolder.answerAuthorTextView.setText(model.getAuthor());
//        answerHolder.answerTimeStampTextView.setText(model.getTimestamp());
//        answerHolder.answerScoreTextView.setText(String.valueOf(model.getAnswerScore()));

        return convertView;
    }
}
