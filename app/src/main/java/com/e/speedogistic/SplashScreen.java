package com.e.speedogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    private String email,password,role;
    private SharedPreferences preferences;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
        linearLayout=(LinearLayout)findViewById(R.id.LinearLayout);
        linearLayout.setAlpha(0f);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.animate().alpha(1f).setDuration(1000);




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        email = preferences.getString("email", "");
                        password = preferences.getString("pass", "");
                        role=preferences.getString("role","");

                        if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && role.equalsIgnoreCase("Client"))
                        {
                            Intent i = new Intent(SplashScreen.this, UserMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else if(!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && role.equalsIgnoreCase("Transporter"))
                        {
                            Intent intent=new Intent(getApplicationContext(),TransActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Intent i2 = new Intent(SplashScreen.this, IntroActivity.class);
                            startActivity(i2);
                            finish();
                        }
                    }

                },2000);



            }
        }


