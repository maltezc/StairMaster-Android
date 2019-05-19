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
import com.google.firebase.firestore.FirebaseFirestore;
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



        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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



        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");

        final User userInfo = new User(firstName, lastName, userName, userEmail);

        // block below sets user docid in database to be registered email instead of randomized code of strings.
        rootRef.collection("Users").document(userEmail).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: user created");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

//        usersRef.add(userInfo);


//        mAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressBar.setVisibility(View.GONE);
//                if (task.isSuccessful()) {


//                    CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");
//                    usersRef.add(new User(userName, firstName, lastName, userEmail));
                    // block below sets user docid in database to be registered email instead of randomized code of strings.
//                    rootRef.collection("Users").document(userEmail).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "onSuccess: user created");
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: " + e.toString());
//                        }
//                    });

                    FirebaseUser user = mAuth.getCurrentUser();

//                    mAuthListener = new FirebaseAuth.AuthStateListener() {
//                        @Override
//                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            if (user != null) {
//                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(userName).build();
//                                user.updateProfile(profileChangeRequest);
//
//                            }
//                        }
//                    };
//
//                    mAuth.addAuthStateListener(mAuthListener); // need this to change info on Firebase Firestore

//                    String usernameTest = user.getDisplayName();



                    Toast.makeText(SignUpActivity.this, "info saved hopefully", Toast.LENGTH_SHORT).show();

//
                    // sets user's document ID in Firebase to be email. Otherwise would be randomly generated series of strings.
//                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(SignUpActivity.this, "Registration success!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    

//                    finish();
//                    startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));
//
//
//                } else {
//
//                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
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

