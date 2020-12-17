package com.e.speedogistic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.e.speedogistic.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class TransActivity extends AppCompatActivity {
SharedPreferences preferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Transporters");
        setSupportActionBar(toolbar);
        preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
        editor=preferences.edit();




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trans, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {


        switch (item.getItemId())
        {
            case R.id.translogout:
                Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_LONG).show();
                FirebaseAuth muth=FirebaseAuth.getInstance();
                muth.signOut();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}