package com.example.stairmaster;

import android.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import fragments.QuestionFragment;


public class QuestionProfileActivity2 extends AppCompatActivity {

    private QuestionFragment questionFragment;


    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_profile2);

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

        questionFragment  = new QuestionFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.questionFragment, questionFragment);


    }


}
