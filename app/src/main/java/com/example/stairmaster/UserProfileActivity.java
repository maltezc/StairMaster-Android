package com.example.stairmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.stairmaster.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;

    TextView userNameTextView;
    EditText userNameEditText;
    TextView firstNameTextView;
    EditText firstNameEditText;
    TextView lastNameTextView;
    EditText lastNameEditText;
    TextView emailTextView;
    EditText emailEditText;
    RecyclerView questionListView;
    private QuestionAdapter mQuestionRecyclerViewAdapter;
    private TextView textViewData;


    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("Questions");
    private ArrayList<Question> mQuestions = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser mAuthUser;
    String mAuthUserId;

//    ActivityUserProfileBinding activityUserProfileBinding;
    private User mUser;

    private boolean mShowSaveIcon;

    private static final String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//        activityUserProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

//        User user = new User();

        userNameTextView = findViewById(R.id.userNameTextViewID);
        userNameEditText = findViewById(R.id.userNameEditTextID);
        firstNameTextView = findViewById(R.id.firstNameTextViewID);
        firstNameEditText= findViewById(R.id.firstNameEditTextID);
        lastNameTextView= findViewById(R.id.lastNameTextViewID);
        lastNameEditText= findViewById(R.id.lastNameEditTextID);
        emailTextView= findViewById(R.id.emailTextViewID);
        emailEditText= findViewById(R.id.emailEditTextID);

        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthUserId = FirebaseAuth.getInstance().getUid();
        textViewData = findViewById(R.id.text_view_data);


        setUpRecyclerView();
        setTitle("Profile");

//        getIncomingIntent();

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(UserProfileActivity.this, SignInActivity.class));
        } else {
            mQuestionRecyclerViewAdapter.startListening();
            loadUserInformation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQuestionRecyclerViewAdapter.stopListening();
    }

    private void loadUserInformation() {
        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference usersRef = rootRef.collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()) {
                String firstNameString = documentSnapshot.getString("firstName");
                String lastNameString = documentSnapshot.getString("lastName");
                String userNameString = documentSnapshot.getString("userName");

                firstNameEditText.setText(firstNameString);
                firstNameTextView.setText(firstNameString);
                lastNameEditText.setText(lastNameString);
                lastNameTextView.setText(lastNameString);
                userNameTextView.setText(userNameString);

            } else {
                Toast.makeText(UserProfileActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
            }

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfileActivity.this, "Loading User info Failed", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: loading user info failed because of " + e);
            }
        });



        firstNameTextView.setText("please set first name");

        lastNameTextView.setText("please set last name");


        if (userFirebaseEmail != null) {
            emailTextView.setText(userFirebaseEmail);
        }
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemSave = menu.findItem(R.id.save_profile);
        itemSave.setVisible(mShowSaveIcon);
        menu.findItem(R.id.edit_profile).setVisible(!mShowSaveIcon);  // you can use negation of the same flag if one and only one of two menu items is visible; or create more complex logic

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_userprofile_menu, menu);
        MenuItem itemSave = menu.findItem(R.id.save_profile);
        itemSave.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_profile:
                mShowSaveIcon = false;
                updateUser();
                break;

            case R.id.edit_profile:
                item.setVisible(false);
                enableEditMode();
                mShowSaveIcon = true;
                break;

        }
        invalidateOptionsMenu();
        return true;
    }

    private void enableEditMode(){
        String userNameString = mAuth.getCurrentUser().getDisplayName();
        userNameTextView.setVisibility(View.GONE);
        userNameEditText.setVisibility(View.VISIBLE);
        userNameEditText.setText(userNameString);
        userNameEditText.requestFocus();

        firstNameTextView.setVisibility(View.GONE);
        firstNameEditText.setVisibility(View.VISIBLE);

        lastNameTextView.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.VISIBLE);
    }



    private void updateUser(){
        User user = createUserObj();
        updateUserToCollection(user);
    }

    private User createUserObj(){
        final User user = new User();
        return user;
    };
    // not sure if we need this one for user profile edits

    private void updateUserToCollection(User user) {

        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
        final String userName = mAuth.getCurrentUser().getDisplayName();
        final String userNameUpdated = userNameEditText.getText().toString().trim();
        final String firstNameUpdated = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String lastNameUpdated = lastNameEditText.getText().toString().trim();
//        final List<Question> questionsUsersList = user.getQuestions();


        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****


        docRef.update("firstName", firstNameUpdated).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: First name text updated. it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
            }
        });

        docRef.update("lastName", lastNameUpdated).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: First name text updated. it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
            }
        });

        Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setUpRecyclerView(){

        final String userName = mAuth.getCurrentUser().getDisplayName();

        Query query5 = questionRef.whereEqualTo("author", userName);
//        Query query = questionRef.orderBy("priority", Query.Direction.DESCENDING); // <---original

  
        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query5, Question.class)
                .build();

        mQuestionRecyclerViewAdapter = new QuestionAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mQuestionRecyclerViewAdapter);

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
                mQuestionRecyclerViewAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        mQuestionRecyclerViewAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position, String itemId) {
                Question question = documentSnapshot.toObject(Question.class);
                String id = documentSnapshot.getId();
                Log.d(TAG, "onItemClick: " + id);

                String path = documentSnapshot.getReference().getPath();

                Toast.makeText(UserProfileActivity.this, "Position: " + position + " ID:" + id, Toast.LENGTH_SHORT).show();
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

