package fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stairmaster.MyAnswersRecyclerViewAdapter;
import com.example.stairmaster.R;
import com.example.stairmaster.adapters.AnswerAdapter;
import com.example.stairmaster.models.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class AnswersFragment2 extends ListFragment {

    private static final String TAG = "AnswersFragment2";

    private View AnswersListView;
    private RecyclerView answersListRecyclerView;
    private DatabaseReference mDatabase;

    private FirestoreRecyclerAdapter<Answer, AnswerAdapter.AnswerHolder> mAnswerAdapter;
    private LinearLayoutManager mManager;
    private AnswersFragment2.OnListFragmentInteractionListener mListener;



    public AnswersFragment2() {
        // Requred empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);

        View AnswersListView =  inflater.inflate(R.layout.fragment_answers_list, container, false);
        answersListRecyclerView = (RecyclerView) AnswersListView.findViewById(R.id.answersRecyclerViewID); 
        answersListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return AnswersListView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        answersListRecyclerView.setLayoutManager(mManager);

        //Firebase Firestore
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        CollectionReference questionRef = firestoreDB.collection("Answers");

        setUpQuestionRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAnswerAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        mAnswerAdapter.stopListening();
    }

    private void setUpQuestionRecyclerView() {

        // vars
        ArrayList<Answer> mAnswers = new ArrayList<>();
        AnswerAdapter mAnswerAdapter;
//        MyAnswersRecyclerViewAdapter mAnswerAdapter;
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
//        mAnswerAdapter = new MyAnswersRecyclerViewAdapter(mAnswers, options);//TODO: look at github post list fragment example and copy!!!!!!!!
        mAnswerAdapter = new AnswerAdapter(options);
//        mAnswerAdapter = new MyAnswersRecyclerViewAdapter(options);


        RecyclerView answerRecyclerView = AnswersListView.findViewById(R.id.answersRecyclerViewID);
        answerRecyclerView.setHasFixedSize(true);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        answerRecyclerView.setAdapter(mAnswerAdapter);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnswersFragment2.OnListFragmentInteractionListener) {
            mListener = (AnswersFragment2.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Answer answer);
//        void onListFragmentInteraction(DummyItem item);
    }


}
