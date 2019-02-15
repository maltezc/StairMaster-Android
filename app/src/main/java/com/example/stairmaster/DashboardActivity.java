package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    static List<String> questionList;
    ListView questionListView;
    String answerString;
    TextView editText;
    Bundle bundle;

    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        questionListView = (ListView)findViewById(R.id.questionListView);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");


        if (questionList==null) {
            questionList = new ArrayList<String>();
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionList);



        bundle = getIntent().getExtras();
        if (answerString != null) {
            answerString = bundle.getString("answerText");
            Log.i("info", answerString);
            questionList.add(answerString);
        } else {
            Toast.makeText(this, "nothing was passed", Toast.LENGTH_SHORT).show();
        }
        questionListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }

    public void newQuestionButton(View view) {

        Intent intent = new Intent(DashboardActivity.this, NewQuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }

        return true;
    }

}
