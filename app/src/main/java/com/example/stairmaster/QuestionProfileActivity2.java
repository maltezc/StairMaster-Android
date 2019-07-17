package com.example.stairmaster;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.adapters.AnswerAdapter;
import com.example.stairmaster.adapters.QuestionAdapter;
import com.example.stairmaster.models.Answer;
import com.example.stairmaster.models.Question;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import fragments.AnswersFragment2;
import fragments.QuestionFragment;


//public class QuestionProfileActivity2 extends AppCompatActivity {
public class QuestionProfileActivity2 extends AppCompatActivity implements AnswersFragment2.OnListFragmentInteractionListener {

    //firebase
    FirebaseAuth mAuth;

    private QuestionFragment questionFragment;
//    private AnswersFragment2 answersListFragment;
    private ImageButton mCheck;
    private ImageButton mBackArrow;
    private ImageButton mAnswerUpVoteButton;
    private ImageButton mAnswerDownVoteButton;
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference answerRef = firestoreDB.collection("Answers");
    private ArrayList<Answer>mAnswers = new ArrayList<>();
    private RecyclerView mAnswerRecyclerView;
    private AnswerAdapter mAnswerRecyclerViewAdapter;

    private static final String TAG = "QuestionProfActivity2";



    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile2);

        firestoreDB = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mAnswerRecyclerView = (RecyclerView) findViewById(R.id.answerRecyclerViewIdQP2);
        mAnswerUpVoteButton = findViewById(R.id.answerUpVoteId);
        mAnswerDownVoteButton = findViewById(R.id.answerDownVoteId);

//        getIncomingIntent();
        setUpAnswerRecyclerView();


//        FragmentManager fm = getFragmentManager();
//
//        fragment = fm.findFragmentById(R.id.questionFragment);
//        if (fragment == null) {
//            fragment = new MyFragment();
//
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.questionFragment, fragment, "myfragment");
//            ft.commit();
//        }


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        ExampleFragment fragment = new ExampleFragment();
//        fragmentTransaction.add(R.id.questionFragment, fragment);
//        fragmentTransaction.commit();

        questionFragment  = new QuestionFragment(); // IN USE
//        answersListFragment = new AnswersFragment2();
//        answersListFragment = new AnswersFragment();
        getSupportFragmentManager().beginTransaction() // in use
                .add(R.id.questionFragment, questionFragment); // in use
//                .add(R.id.answersRecyclerViewID, answersListFragment);

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.answersListFragment, answersListFragment);
//                .add(list, answersListFragment);
//                .add(R.id.answersRecyclerViewID, answersListFragment);





    }


    @Override
    public void onListFragmentInteraction(Answer answer) {

    }

    private void upVoteClicked() {
        Log.d(TAG, "upVoteClicked: Up Vote Clicked");

    }

    private void downVoteClicked() {
        Log.d(TAG, "downVoteClicked: DownVote Clicked");

    }

    private void setUpAnswerRecyclerView() {
//        final String userName = mAuth.getCurrentUser().getDisplayName();
//        Query query = answerRef.whereEqualTo("author", userName);

        String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");
        Query query = answerRef.whereEqualTo("parentQuestionId", questionPathIDString);
//        Query query = answerRef.orderBy("score", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Answer> options = new FirestoreRecyclerOptions.Builder<Answer>()
                .setQuery(query, Answer.class)
                .build();

        mAnswerRecyclerViewAdapter = new AnswerAdapter(options);

        RecyclerView answerRecyclerView = findViewById(R.id.answerRecyclerViewIdQP2);
        answerRecyclerView.setHasFixedSize(true);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerRecyclerView.setAdapter(mAnswerRecyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mAnswerRecyclerViewAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(answerRecyclerView);


//        //TODO: Below does not work with upvote/downvote. Getting closer though.
//        mAnswerRecyclerViewAdapter.setOnItemClickListener(new AnswerAdapter.OnItemClickListener(){
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position, String id) {
//
//                Answer answer = documentSnapshot.toObject(Answer.class);
//                String answerId = documentSnapshot.getId();
//                Log.d(TAG, "onItemClick: " + answerId);
//                Toast.makeText(QuestionProfileActivity2.this, "answerId" + answerId, Toast.LENGTH_SHORT).show();
//            }
//        });

/**
 *question example for reference below
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
 **/

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAnswerRecyclerViewAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAnswerRecyclerViewAdapter.stopListening();

//        super.onStop();
//        if (mQuestionAdapter != null) {
//            mQuestionAdapter.stopListening();
//        }
    }
}
