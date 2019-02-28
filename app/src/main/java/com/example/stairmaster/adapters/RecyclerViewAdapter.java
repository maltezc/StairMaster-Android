package com.example.stairmaster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stairmaster.R;
import com.example.stairmaster.models.Note;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Note> mNotes = new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<Note> notes) {
        this.mNotes = notes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item, viewGroup, false);
        return new ViewHolder(view);
//        return null; comes standard with this horrible line which throws null error.
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onBindViewholder: called.");

        viewHolder.timestamp.setText(mNotes.get(i).getTimestamp());
        viewHolder.title.setText(mNotes.get(i).getTitle());
    }

    @Override
    public int getItemCount() {

        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        TextView timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_timestamp);
        }
    }


}
