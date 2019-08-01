package com.example.stairmaster;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stairmaster.logins.SignInActivity;
import com.example.stairmaster.models.Question;
import com.example.stairmaster.models.User;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.StructuredQuery;

import java.util.ArrayList;
import java.util.List;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    ProgressBar progressBar;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUserName;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference databaseReference;
    private FirebaseFirestore rootRef;
    String mAuthUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        editTextUserName = (EditText) findViewById(R.id.editTextUserNameID);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mAuthUserId = FirebaseAuth.getInstance().getUid();

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        if (mAuth.getCurrentUser() != null) {
            // handle user already logged in
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAuth.addAuthStateListener(mAuthListener);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    private void registerUser() {

        final String userEmail = editTextEmail.getText().toString().trim();
        final String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final List<Question> questionsList = new ArrayList<>();
        final String firstName = "default firstName";
        final String lastName = "default lastName";
//        final Uri userProfilePicture = "";
        //TODO: SET POINTS TO 0



        if (userEmail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


            // reference below for adding userID to firebase
//        answerRef.add(answerInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d(TAG, "onSuccess: Answer was added");
//
//                String answerIdRef  = documentReference.getId();
//                documentReference.update("answerFirebaseId", answerIdRef).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: answerFirebaseId added to database");
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: " + e.toString());
//
//            }
//        });



        final CollectionReference usersColRef = FirebaseFirestore.getInstance().collection("Users");

        final User userInfo = new User(firstName, lastName, userName, userEmail, mAuthUserId);

        // block below sets user docid in database to be registered email instead of randomized code of strings.
        // TODO: 2019-07-31 set user's id here onaction complete. See answersetId for similar relationship.
        rootRef.collection("Users").document(userEmail).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
//            public void onSuccess(DocumentReference documentReference) {
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: user was created");
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, SignInActivity.class));
                break;
        }
    }
}

