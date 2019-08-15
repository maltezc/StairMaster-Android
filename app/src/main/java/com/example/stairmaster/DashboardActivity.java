package com.example.stairmaster;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    // data binding
//    ActivityDashboardBinding mBinding;

    private Question mQuestion;

    RecyclerView questionListView;
    private EditText editTextTags;
    private TextView textViewData;

    // shit should always be declared up here and then initialized down in OnCreate UON to avoid null pointer exceptions
    private static final String TAG = "DashboardActivity";

    //Firebase Firestore
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = firestoreDB.collection("Questions");

    // UI components
    private RecyclerView mRecyclerView; // m is prepended to global variables

    // vars
    private ArrayList<Question> mQuestions = new ArrayList<>();
    private QuestionAdapter mQuestionAdapter;

    FirebaseAuth mAuth;
    FirebaseUser mAuthUser;
    String mAuthUserId;

    EditText answerEditText;

    String userName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        Question questions = new Question();

        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);
        editTextTags = findViewById(R.id.edit_text_tags);
        textViewData = findViewById(R.id.text_view_data);

        setTitle("Dashboard");
        Log.i("info","Dashboard started");

        findViewById(R.id.fabNewQuestion);

        FloatingActionButton buttonAddQuestion = findViewById(R.id.fabNewQuestion);
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashboardActivity.this, NewQuestionActivity.class));
            }
        });

        final FloatingActionButton buttonBeginQuiz = findViewById(R.id.fabBeginQuiz);
        buttonBeginQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, QuizActivity.class));
            }
        });

        setUpQuestionRecyclerView();

//        addUserIdtoFirebase();
    }



    // TODO: 2019-08-01 figure out how to add user id to firebase DB
    private void addUserIdtoFirebase() {
        if (mAuth.getCurrentUser() != null) {
            String userEmail = mAuth.getCurrentUser().getEmail();
            final CollectionReference usersColRef = FirebaseFirestore.getInstance().collection("Users");
            final DocumentReference userDocRef = usersColRef.document(userEmail);
            userDocRef.update("userEmail", userEmail);
        }
//        else {
//            Log.d(TAG, "onCreate: no one is logged in");
//
//        }
    }





    @Override
    protected void onStart() {
        super.onStart();
        mQuestionAdapter.startListening();





    }

    @Override
    protected void onStop() {
        super.onStop();
        mQuestionAdapter.stopListening();

//        super.onStop();
//        if (mQuestionAdapter != null) {
//            mQuestionAdapter.stopListening();
//        }
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

                Toast.makeText(this, "going to user's profile", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private void setUpQuestionRecyclerView(){
        Query query = questionRef.orderBy("questionPriority", Query.Direction.DESCENDING); // field questionPriority is VERY IMPORTANT. if it doesnt match the models category, no items will be displayed.
        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
                .build();

        mQuestionAdapter = new QuestionAdapter(options);

        RecyclerView questionRecyclerView = findViewById(R.id.questionRecyclerView);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionRecyclerView.setAdapter(mQuestionAdapter);

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
                mQuestionAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(questionRecyclerView);

        mQuestionAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
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

}
