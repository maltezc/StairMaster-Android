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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    TextView memberCreatedTimestampTextView;
    RecyclerView questionListView;
    private QuestionAdapter mQuestionRecyclerViewAdapter;
    private TextView textViewData;
    ImageView userProfilePhoto;



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
        firstNameEditText = findViewById(R.id.firstNameEditTextID);
        lastNameTextView = findViewById(R.id.lastNameTextViewID);
        lastNameEditText = findViewById(R.id.lastNameEditTextID);
        emailTextView = findViewById(R.id.emailTextViewID);
        emailEditText = findViewById(R.id.emailEditTextID);
        memberCreatedTimestampTextView = findViewById(R.id.memberCreatedTimestampID);

        questionListView = (RecyclerView) findViewById(R.id.questionRecyclerView);

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
//        mAuthUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // TODO: 2019-07-31 have signup send you to profile and be able to put in profile stuff
        textViewData = findViewById(R.id.text_view_data);
        userProfilePhoto = (ImageView) findViewById(R.id.userProfileImageView);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        if (mAuth.getCurrentUser() != null) {
            getIncomingIntent();
            setUpQuestionRecyclerView();
            setTitle("Profile");
            setUserId();
            getUserActionHistory();
        } else {
            finish();
            startActivity(new Intent(UserProfileActivity.this, SignInActivity.class));
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
        }

    }

    private void setUserId() {
        final CollectionReference usersColRef = FirebaseFirestore.getInstance().collection("Users");
        String userEmail = mAuth.getCurrentUser().getEmail();
        final String userFirebaseId = mAuth.getCurrentUser().getUid();

        final DocumentReference userDocRef = usersColRef.document(userEmail);
        userDocRef.update("userFirebaseId", userFirebaseId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: userFirebaseId added to firebase database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failed to create user in signup" + e);
            }
        });
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


        mAuthUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final CollectionReference usersRef = rootRef.collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(userProfilePhoto);
            }
        }


        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()) {


                String firstNameString = documentSnapshot.getString("firstName");
                String lastNameString = documentSnapshot.getString("lastName");
                String userNameString = documentSnapshot.getString("userName");
                String memberTimestamp = documentSnapshot.getString("userCreatedTimestamp");

                firstNameEditText.setText(firstNameString);
                firstNameTextView.setText(firstNameString);
                lastNameEditText.setText(lastNameString);
                lastNameTextView.setText(lastNameString);
                userNameTextView.setText(userNameString);
                memberCreatedTimestampTextView.setText((memberTimestamp));

//                if (user.getPhotoUrl() != null) {
//
//                    Glide.with(this)
//                            .load(user.getPhotoUrl().toString())
//                            .into(userProfilePhoto);
//                }

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
        docRef.update("userFirebaseId", mAuthUserId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: FirebaseUserId added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: FirebaseUserId was not added" + e);
            }
        });

        Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setUpQuestionRecyclerView(){
        getIncomingIntent();

//        if(getIntent().hasExtra("userNameString")) {
//            String userName = (String) getIntent().getExtras().get("userNameString").toString();
//
//        } else if (mAuth.getCurrentUser() != null){
//            final String userName = mAuth.getCurrentUser().getDisplayName();
//        } else {
//            String userName = "Please enter a username";
//        }


        final String userName = mAuth.getCurrentUser().getDisplayName(); // TODO: 2019-08-01 user get to pull username field from firebaseDB
//        final String userName =
//        String userName = (String) getIntent().getExtras().get("userNameString").toString();
        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final CollectionReference usersRef = rootRef.collection("Users");
        DocumentReference docRef = usersRef.document(userFirebaseEmail); // <-- this works!****
//        String userName = (String) userNameTextView.getText();

//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                String userName = documentSnapshot.get("userName").toString();
//            }
//        });

//        Query query = questionRef.whereEqualTo("userEmail", userFirebaseEmail);
//        Query query = questionRef.whereEqualTo("userEmail", mAuth.getCurrentUser().getEmail());
        Query query = questionRef.whereEqualTo("questionAuthor", userName);
//        Query query = questionRef.orderBy("priority", Query.Direction.DESCENDING); // <---original


        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
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


    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("userNameString")) {
            String userName = (String) getIntent().getExtras().get("userNameString").toString();

        }


        /*
        if (getIntent().hasExtra("question_string")) {

            String questionString = (String) getIntent().getExtras().get("question_string").toString();
            String questionAuthorString = (String) getIntent().getExtras().get("questionAuthor_string").toString();
            String questionPathIDString = (String) getIntent().getExtras().get("questionID_string");

            questionContentTextView.setText(questionString);
            questionAuthorTextView.setText(questionAuthorString);
        }
         */
    }


    private void getUserActionHistory() {

        // TODO: 2019-08-03 be able to reach into firebase and get id's of items listed regardless of collection. <--action history list created <-- DONE

        // TODO: 2019-08-06 if collection type is an issue, then figure it out


        // TODO: 2019-08-06 make add elementId to userActionHistoryList <-- DONE
            //upvote,
            // downvote,
            // comments,
            // questions, TODO: DONE
            // answers, TODO: DONE
            // checked answers

        //pull user
        String userEmail = mAuth.getCurrentUser().getEmail();
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Users").document(userEmail);


        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.contains("actionHistory")) {
                        List<String> group = (List<String>) documentSnapshot.get("actionHistory");
                        Log.d(TAG, "onComplete: Listing user's actionHistory " + group);

//                        split action history into values x and y
//                        for x,y in action history list
//                              if x = collection and y = refId
//                                    retrieve y collection in x
//                                         create card in recycler view

//                        DocumentReference actionHistoryDocRef = FirebaseFirestore.getInstance().document("vv6I9Z4wcY0um5j8y2Ry");
//                        Log.d(TAG, "onComplete: action history has " + actionHistoryDocRef);





                    } else {
                        Log.d(TAG, "onComplete: user has no action History");
                    }





                }
            }
        });


    }
}

