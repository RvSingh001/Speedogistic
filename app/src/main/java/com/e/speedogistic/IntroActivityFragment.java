package com.e.speedogistic;

import android.content.Intent;
import android.content.SharedPreferences;
import  android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;



import static android.content.Context.MODE_PRIVATE;


public class IntroActivityFragment extends Fragment {

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        editor.commit();


        Button signin = (Button) view.findViewById(R.id.Signin);
        Button signup = (Button) view.findViewById(R.id.signup);


        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SignInPage.class));

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SignUpActivity.class));

            }
        });

         return view;

    }
}
