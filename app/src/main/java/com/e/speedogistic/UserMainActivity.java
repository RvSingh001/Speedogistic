package com.e.speedogistic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
        editor=preferences.edit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
       init();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.pre_book, R.id.profile, R.id.feedback,R.id.trans)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.user_main, menu);
       menu.getItem(0).setIcon(R.drawable.ic_baseline_power_settings_new_24);
      return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        item.setIcon(R.drawable.ic_baseline_power_settings_new_24);
        switch (item.getItemId())
        {
//            case R.id.profilemy:
//                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
//                return true;

            case R.id.logout:
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void intinav()
    {

    }
    public void init()
    {
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        String Userid = mAuth.getCurrentUser().getUid();
        final FirebaseUser user=mAuth.getCurrentUser();
        final DocumentReference documentReference=firebaseFirestore.collection("Users").document(Userid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();
                Map<String, Object> map = (Map<String, Object>) documentSnapshot.getData();
                View view = navigationView.getHeaderView(0);
                TextView name = view.findViewById(R.id.name);
                TextView email = view.findViewById(R.id.email);
                TextView verify = view.findViewById(R.id.verify);
                Button verlink = view.findViewById(R.id.resend);
                email.setText(map.get("email").toString());
                String username=map.get("firstName").toString()+" "+map.get("lastName").toString();
                name.setText(username);
                verlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Verification Email Sent Check Your Inbox",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });

                if (user.isEmailVerified())
                {

                    verify.setText(" User Verified");
                    verlink.setVisibility(View.INVISIBLE);

                }


            }
        });


    }
}







