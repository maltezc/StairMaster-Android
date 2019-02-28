package com.example.stairmaster;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.stairmaster.adapters.RecyclerViewAdapter;
import com.example.stairmaster.models.Note;
import com.example.stairmaster.util.VerticalSpacingItemDecorator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    static List<String> questionList;
//    ListView questionListView;
    RecyclerView questionListView;
    String answerString;
    TextView textView;
    Button submitQuestionButton;

    ArrayAdapter arrayAdapter;
    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions
    private static final String TAG = "DashboardActivity";

    // UI components
    private RecyclerView mRecyclerView; // m is prepended to global vaiables

    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private RecyclerViewAdapter mNoteRecyclerAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mRecyclerView = findViewById(R.id.questionRecyclerView);

        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButton);
        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView2);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");




        initRecyclerView();
        insertFakeNotes();

    }




    public void newQuestionButton(View view) {

        Intent intent = new Intent(DashboardActivity.this, NewQuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, SignInActivity.class));
                break;

            case R.id.menuProfile:
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }

        return true;
    }


    private void insertFakeNotes() {
        for (int i = 0; i < 10; i++){
            Note note = new Note();
            note.setTitle("title # " + i);
            note.setContent("content #: " + i);
            note.setTimestamp("Jan 2019");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mNoteRecyclerAdapter = new RecyclerViewAdapter(mNotes);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

}
