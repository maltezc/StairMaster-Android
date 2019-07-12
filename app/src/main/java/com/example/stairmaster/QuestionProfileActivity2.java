package com.example.stairmaster;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stairmaster.models.Answer;

import fragments.AnswersFragment2;
import fragments.QuestionFragment;


//public class QuestionProfileActivity2 extends AppCompatActivity {
public class QuestionProfileActivity2 extends AppCompatActivity implements AnswersFragment2.OnListFragmentInteractionListener {

    private QuestionFragment questionFragment;
    private AnswersFragment2 answersListFragment;


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
        answersListFragment = new AnswersFragment2();
//        answersListFragment = new AnswersFragment();
        getSupportFragmentManager().beginTransaction() // in use
                .add(R.id.questionFragment, questionFragment); // in use
//                .add(R.id.answersRecyclerViewID, answersListFragment);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.answersListFragment, answersListFragment);
//                .add(list, answersListFragment);
//                .add(R.id.answersRecyclerViewID, answersListFragment);





    }


    @Override
    public void onListFragmentInteraction(Answer answer) {

    }
}
