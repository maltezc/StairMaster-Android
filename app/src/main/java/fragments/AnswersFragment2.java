package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.MyAnswersRecyclerViewAdapter;
import com.example.stairmaster.R;
import com.example.stairmaster.models.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class AnswersFragment2 extends Fragment {

    private View AnswersListView;
    private RecyclerView answersListRecyclerView;



    public AnswersFragment2() {
        // Requred empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View AnswersListView =  inflater.inflate(R.layout.fragment_answers_list, container, false);
        answersListRecyclerView = (RecyclerView) AnswersListView.findViewById(R.id.answersRecyclerViewID);
        answersListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return AnswersListView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Firebase Firestore
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionRef = firestoreDB.collection("Answers");

        setUpQuestionRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void setUpQuestionRecyclerView() {

        // vars
        ArrayList<Answer> mAnswers = new ArrayList<>();
        MyAnswersRecyclerViewAdapter mAnswerAdapter;
//        AnswersFragment.OnListFragmentInteractionListener listener = new AnswersFragment.OnListFragmentInteractionListener() {
//            @Override
//            public void onListFragmentInteraction(Answer answer) {
//
//
//            }
//        };

        //Firebase Firestore
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionRef = firestoreDB.collection("Answers");

        Query query = questionRef.orderBy("score", Query.Direction.DESCENDING); // field questionPriority is VERY IMPORTANT. if it doesnt match the models category, no items will be displayed.


        FirestoreRecyclerOptions<Answer> options = new FirestoreRecyclerOptions.Builder<Answer>()
                .setQuery(query, Answer.class)
                .build();

//        mAnswerAdapter = new AnswerAdapter(models,(getContext()) );
        mAnswerAdapter = new MyAnswersRecyclerViewAdapter(mAnswers, options);//TODO: look at github post list fragment example and copy!!!!!!!!
//        mAnswerAdapter = new MyAnswersRecyclerViewAdapter(options);


        RecyclerView answerRecyclerView = AnswersListView.findViewById(R.id.answersRecyclerViewID);
        answerRecyclerView.setHasFixedSize(true);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        answerRecyclerView.setAdapter(mAnswerAdapter);


    }
}
