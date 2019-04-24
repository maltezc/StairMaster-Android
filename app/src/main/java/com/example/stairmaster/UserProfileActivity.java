package com.example.stairmaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.stairmaster.databinding.ActivityUserProfileBinding;
import com.example.stairmaster.logins.SignInActivity;
import com.example.stairmaster.models.Question;
import com.example.stairmaster.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

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


    FirebaseAuth mAuth;

    ActivityUserProfileBinding activityUserProfileBinding;
    private User mUser;

    private boolean mShowSaveIcon;

    private static final String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//        activityUserProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

        User user = new User();

        userNameTextView = findViewById(R.id.userNameTextViewID);
        userNameEditText = findViewById(R.id.userNameEditTextID);
        firstNameTextView = findViewById(R.id.firstNameTextViewID);
        firstNameEditText= findViewById(R.id.firstNameEditTextID);
        lastNameTextView= findViewById(R.id.lastNameTextViewID);
        lastNameEditText= findViewById(R.id.lastNameEditTextID);
        emailTextView= findViewById(R.id.emailTextViewID);
        emailEditText= findViewById(R.id.emailEditTextID);


        mAuth = FirebaseAuth.getInstance();

        setTitle("Profile");



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(UserProfileActivity.this, SignInActivity.class));
        } else {
            loadUserInformation();
        }
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();
        String userDisplayNameFB = mAuth.getCurrentUser().getDisplayName();
        String userFirebaseEmail = mAuth.getCurrentUser().getEmail();
        String firebaseUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();


        if (userDisplayNameFB != null) {
            userNameTextView.setText(userDisplayNameFB.toString());
        }
        if (userFirebaseEmail != null) {
            emailTextView.setText(userFirebaseEmail.toString());
        }

    }

//    private void loadUserInformation() {
//        final FirebaseUser user = mAuth.getCurrentUser();
//
//        if (user != null) {
//            if (user.getPhotoUrl() != null) {
//                Glide.with(this)
//                        .load(user.getPhotoUrl().toString())
//                        .into(imageView);
//            }
//
//            if (user.getDisplayName() != null) {
//                editText.setText(user.getDisplayName());
//            }
//
//            if (user.isEmailVerified()) {
//                textView.setText("Email Verified");
//            } else {
//                textView.setText("Email Not Verified (Click to Verify)");
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(UserProfileActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    }


//    private void saveUserInformation() {
//
//        String displayName = editText.getText().toString();
//
//        if (displayName.isEmpty()) {
//            editText.setError("Name required");
//            editText.requestFocus();
//            return;
//        }
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        if (user != null && profileImageUrl != null) {
//            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(displayName)
//                    .setPhotoUri(Uri.parse(profileImageUrl))
//                    .build();
//
//            user.updateProfile(profile)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(UserProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            uriProfileImage = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
//                imageView.setImageBitmap(bitmap);
//
//                uploadImageToFirebaseStorage();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void uploadImageToFirebaseStorage() {
//        StorageReference profileImageRef =
//                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
//
//        if (uriProfileImage != null) {
//            progressBar.setVisibility(View.VISIBLE);
//            profileImageRef.putFile(uriProfileImage)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressBar.setVisibility(View.GONE);
//                            profileImageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menuLogout:
//
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                startActivity(new Intent(this, SignInActivity.class));
//                break;
//
//            case R.id.menuDashBoard:
//                Intent intent = new Intent(this, DashboardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//        }
//
//        return true;
//    }
//
//    private void showImageChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
//    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemSave = menu.findItem(R.id.save_profile);
        itemSave.setVisible(mShowSaveIcon);
        menu.findItem(R.id.edit_question).setVisible(!mShowSaveIcon);  // you can use negation of the same flag if one and only one of two menu items is visible; or create more complex logic

//        MenuItem itemEdit = menu.findItem(R.id.edit_question);
//        itemEdit.setVisible(mShowSaveIcon);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_userProfile_menu, menu);
        MenuItem itemSave = menu.findItem(R.id.save_profile);
        itemSave.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_profile:
                mShowSaveIcon = false;
                updateQuestion();
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

        String userNameString = userNameTextView.getText().toString();
        userNameTextView.setVisibility(View.GONE);
        userNameEditText.setVisibility(View.VISIBLE);
        userNameEditText.setText(userNameString);

        String firstNameString = firstNameTextView.getText().toString();
        firstNameTextView.setVisibility(View.GONE);
        firstNameEditText.setVisibility(View.VISIBLE);
        firstNameEditText.setText(firstNameString);

        String lastNameString = lastNameTextView.getText().toString();
        lastNameTextView.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.VISIBLE);
        lastNameEditText.setText(lastNameString);

    }



    private void updateQuestion(){
        User user = createUserObj();
        updateUserToCollection(user);
    }

    private User createUserObj(){
        final User user = new User();
        return user;
    };

    // not sure if we need this one for user profile edits



    private void updateUserToCollection(User user) {

        final String updatedUserNameText = userNameEditText.getText().toString().trim();
        final String updatedFirstNameText = firstNameEditText.getText().toString().trim();
        final String updatedLastNameText = lastNameEditText.getText().toString().trim();


        mAuth.updateCurrentUser(updatedUserNameText, updatedFirstNameText, updatedLastNameText).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    User user = new User (
                            updatedUserNameText,
                            updatedFirstNameText,
                            updatedLastNameText
                    )
                }
            }
        });


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = rootRef.collection("Users");


//        DocumentReference docRef = questionsRef.document(questionPathIDString); // <-- this works!****

        docRef.update("question", updatedQuestionText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Question text updated. it worked");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of: " + e.toString());
                Log.d(TAG, "onFailure: itemID " + questionPathIDString);
            }
        });

        docRef.update("answer", updatedAnswerText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Answer text updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: it failed because of " + e.toString());
                Log.d(TAG, "onFailure: itemID" + questionPathIDString);
            }
        });


        Toast.makeText(this, "Question Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

}

