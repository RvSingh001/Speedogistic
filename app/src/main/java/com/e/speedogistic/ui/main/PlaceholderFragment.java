package com.e.speedogistic.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.e.speedogistic.IntroActivity;
import com.e.speedogistic.R;
import com.google.firebase.auth.FirebaseAuth;


public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar=root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return root;
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.trans, menu);
       super.onCreateOptionsMenu(menu, inflater);

    }



    public boolean onOptionsItemSelected(MenuItem item)
    {

        item.setIcon(R.drawable.ic_baseline_power_settings_new_24);
        switch (item.getItemId())
        {
            case R.id.logout:
                Toast.makeText(getContext(),"Logout",Toast.LENGTH_LONG).show();
                FirebaseAuth muth=FirebaseAuth.getInstance();
                muth.signOut();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(getContext(),IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}