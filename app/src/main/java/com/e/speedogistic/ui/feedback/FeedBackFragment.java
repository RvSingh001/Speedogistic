package com.e.speedogistic.ui.feedback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.e.speedogistic.R;
import com.e.speedogistic.UserMainActivity;
import com.e.speedogistic.ui.pre_book.Pre_bookFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedBackFragment extends Fragment {
    RatingBar ratingBar;
    EditText feedback;
    Button submit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    SharedPreferences preferences1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feeback, container, false);
        progressDialog=new ProgressDialog(getContext());
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        ratingBar=root.findViewById(R.id.rate);
        feedback=root.findViewById(R.id.feedback);
        submit=root.findViewById(R.id.submit);
        preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        preferences1=getActivity().getSharedPreferences("logemail", Context.MODE_PRIVATE);
        editor=preferences1.edit();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(feedback.getText())) {
                    Toast.makeText(getContext(), "Enter Your Feedback", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Feedback");
                    progressDialog.setMessage("Please Wait your feedback is submitting");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    DocumentReference documentReference = firebaseFirestore.collection("Feedback").document();
                    Map<String, Object> map = new HashMap<String, Object>();
                    String rate = String.valueOf(ratingBar.getRating());
                    String email = Pre_bookFragment.email;
                    map.put("email", preferences.getString("email", ""));
                    map.put("name", preferences.getString("username", ""));
                    map.put("rate", rate);
                    map.put("feedback", feedback.getText().toString());
                    map.put("logemail", preferences1.getString("logemail", ""));
                    editor.clear();
                    editor.commit();


                    documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Successfully submitted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getContext(), UserMainActivity.class));
                            getActivity().finish();
                        }
                    });
                }
            }
        });

        return root;
    }
}