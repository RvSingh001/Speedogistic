package com.e.speedogistic.ui.userfeedback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.e.speedogistic.Booking;
import com.e.speedogistic.R;
import com.e.speedogistic.models.BookModel;
import com.e.speedogistic.ui.Transporters.TransFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserFeedback extends Fragment {


    public static String email;

        private RecyclerView recyclerView;
        private FirebaseFirestore firebaseFirestore;
        private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
        FirebaseAuth firebaseAuth;

        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        SharedPreferences preferences1;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_user_feedback, container, false);
            recyclerView = root.findViewById(R.id.frecycleview);
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            preferences=getActivity().getSharedPreferences("logemail", Context.MODE_PRIVATE);
            preferences1=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            editor=preferences.edit();
            final Query query = firebaseFirestore.collection("Feedback").whereEqualTo("logemail",preferences1.getString("email",""));
            final FirestoreRecyclerOptions<userFeedbackModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<userFeedbackModel>().setQuery(query, userFeedbackModel.class)
                    .build();

            firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<userFeedbackModel, UserFeedback.userFeedbackModelViewHolder>(firestoreRecyclerOptions) {
                @NonNull
                @Override
                public UserFeedback.userFeedbackModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedbackitem, parent, false);


                    return new UserFeedback.userFeedbackModelViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull final UserFeedback.userFeedbackModelViewHolder userFeedbackModelViewHolder , int i, @NonNull final userFeedbackModel userFeedbackModel) {

                    userFeedbackModelViewHolder.userfeedback.setText(userFeedbackModel.getFeedback());
                    userFeedbackModelViewHolder.username.setText(userFeedbackModel.getName());
                    userFeedbackModelViewHolder.useremail.setText(userFeedbackModel.getEmail());
                    userFeedbackModelViewHolder.ratingBar.setRating(Float.valueOf(userFeedbackModel.getRate()));



                }
            };
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(firestoreRecyclerAdapter);
            return root;
        }

        private class userFeedbackModelViewHolder extends RecyclerView.ViewHolder {

            private TextView username,useremail,userfeedback;
            private RatingBar ratingBar;

            public userFeedbackModelViewHolder(@NonNull View itemView) {

                super(itemView);
                username=itemView.findViewById(R.id.fusername);
                useremail=itemView.findViewById(R.id.fuseremail);
                userfeedback=itemView.findViewById(R.id.userfeeback);
                ratingBar=itemView.findViewById(R.id.frate);

            }
        }

        public void onStop() {
            super.onStop();
            firestoreRecyclerAdapter.startListening();
        }

        public void onStart() {
            super.onStart();
            firestoreRecyclerAdapter.startListening();
        }
    }