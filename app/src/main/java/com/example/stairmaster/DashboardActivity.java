package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    TextView textView;
    Bundle bundle;
    Button submitQuestionButton;

    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButton);
        questionListView = (ListView)findViewById(R.id.questionListView);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView2);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");


        if (questionList==null) {
            questionList = new ArrayList<String>();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionList);


                answerString = data.getStringExtra("answerText");
                textView.setText(answerString);
                Log.i("info", answerString);
                questionList.add(answerString);
                questionListView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }
            if (resultCode == RESULT_CANCELED) {
                Log.i("info","nothing to pass");

            }
        }
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
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }

        return true;
    }

}
