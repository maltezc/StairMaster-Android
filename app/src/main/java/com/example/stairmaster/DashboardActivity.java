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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ListView questionListView;
    ArrayAdapter adapter;
    String questionString;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Dashboard");

        Log.i("info","Dashboard started");

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        questionString = bundle.getString("questionInfo");

        arrayList = new ArrayList<String>();
        arrayList.add(questionString);

        adapter = new ArrayAdapter<String>(this,R.layout.activity_dashboard,arrayList);
        adapter.notifyDataSetChanged();


        questionListView = (ListView)findViewById(R.id.questionListView);
//        questionListView.setAdapter(adapter);


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
