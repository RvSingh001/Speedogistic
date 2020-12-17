package com.e.speedogistic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.speedogistic.models.UserModel;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignInPage extends AppCompatActivity {
    private ProgressDialog progressDialog;
    Button login;
    EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView reset;
    String Userid;
    UserModel userModel;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
        editor=preferences.edit();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
       // getSupportActionBar().setTitle("Login");




        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        FirebaseUser user = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        login = (Button) findViewById(R.id.signinbutton);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        reset=findViewById(R.id.reset);



reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(email.getText())) {
            Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
        }else
        {
        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           Toast.makeText(getApplicationContext(),"Reset Link Send to Your Email",Toast.LENGTH_LONG).show();
            }
        });
    }}
});


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your password!", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.setTitle("Logging");
                    progressDialog.setMessage("Please wait while we check your credentials.");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
                    progressDialog.show();

                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                       role();
                                        progressDialog.dismiss();


                                } else {
                                    Toast.makeText(getApplicationContext(), "Email or password are wrong", Toast.LENGTH_LONG).show();

                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }


                }



        });
    }
public void role()
{
    Userid = mAuth.getCurrentUser().getUid();
    DocumentReference documentReference=firebaseFirestore.collection("Users").document(Userid);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
       DocumentSnapshot documentSnapshot=task.getResult();
       Log.e("error",documentSnapshot.getData().toString());
       Map<String,Object>newMap =(Map<String, Object>) documentSnapshot.getData();


        String c=newMap.get("role").toString();
        String name=newMap.get("firstName").toString()+" "+newMap.get("lastName").toString();
        editor.putString("email",email.getText().toString());
        editor.putString("pass",password.getText().toString());
        editor.putString("role",newMap.get("role").toString());
        editor.putString("username",name);
        editor.commit();

        if(c.equals("Client"))
        {

            Intent intent=new Intent(getApplicationContext(),UserMainActivity.class);

            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();

        }else if (c.equals("Transporter"))
        {

            Intent intent=new Intent(getApplicationContext(),TransActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }



    }
});


}
}





