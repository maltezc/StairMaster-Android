package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.stairmaster.logins.SignInActivity;
import com.example.stairmaster.models.Note;
import com.example.stairmaster.util.VerticalSpacingItemDecorator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements
        RecyclerViewAdapter.OnNoteListener,
        View.OnClickListener

{

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
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
//        textView = (TextView) findViewById(R.id.textView2);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");

        findViewById(R.id.fab).setOnClickListener(this);

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
        for (int i = 0; i <4; i++){
            Note note = new Note();
            note.setTitle("title # " + i);
            note.setQuestion("question #: " + i);
            note.setAnswer("answer #: " + i);
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
        mNoteRecyclerAdapter = new RecyclerViewAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked" + position);

        //this is where you would navigate to a activity

        Intent intent = new Intent(this, QuestionProfileActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, QuestionProfileActivity.class);
        startActivity(intent);
    }
}
