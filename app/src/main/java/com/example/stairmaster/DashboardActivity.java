package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.stairmaster.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stairmaster.adapters.QuestionAdapter;
import com.example.stairmaster.logins.SignInActivity;
import com.example.stairmaster.models.Question;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    // data binding
    ActivityDashboardBinding mBinding;

    private Question mQuestion;



    RecyclerView questionListView;
    private EditText editTextTags;
    private TextView textViewData;


    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions
    private static final String TAG = "DashboardActivity";


    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("Questions");


    // UI components
    private RecyclerView mRecyclerView; // m is prepended to global vaiables

    // vars
    private ArrayList<Question> mNotes = new ArrayList<>();
    private QuestionAdapter adapter;

    EditText answerEditText;

    String userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        Question questions = new Question();



        mRecyclerView = findViewById(R.id.questionRecyclerView);

        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);
        editTextTags = findViewById(R.id.edit_text_tags);
        textViewData = findViewById(R.id.text_view_data);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");

        findViewById(R.id.fab);

        FloatingActionButton buttonAddQuestion = findViewById(R.id.fab);
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashboardActivity.this, NewQuestionActivity.class));
            }
        });


        setUpRecyclerView();


//        String userNameString = (String) getIntent().getExtras().get("userNameString");
//        String firstNameString = (String) getIntent().getExtras().get("firstNameString");
//        String lastNameString = (String) getIntent().getExtras().get("lastNameString");


    }





    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
//                intent.putExtra("userNameString", userNameString);
//                intent.putExtra("firstNameString", firstNameString);
//                intent.putExtra("lastName", lastNameString);

                Toast.makeText(this, "going to user's profile", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }





    private void setUpRecyclerView(){
        Query query = questionRef.orderBy("priority", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
                .build();

        adapter = new QuestionAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position, String itemId) {
                Question question = documentSnapshot.toObject(Question.class);
                String id = documentSnapshot.getId();
                Log.d(TAG, "onItemClick: " + id);

                String path = documentSnapshot.getReference().getPath();

                Toast.makeText(DashboardActivity.this, "Position: " + position + " ID:" + id, Toast.LENGTH_SHORT).show();


            }
        });
    }


    public void loadQuestions(View v) {
        questionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Question question = documentSnapshot.toObject(Question.class);
                            question.setDocumentID(documentSnapshot.getId());

                            String documentID = question.getDocumentID();

                            data += "ID: " + documentID;

                            for (String tag : question.getTags()) {
                                data += "\n-" + tag;

                            }

                            data += "\n\n";
                            textViewData.setText(data);



                        }
                    }
                });
    }
}
