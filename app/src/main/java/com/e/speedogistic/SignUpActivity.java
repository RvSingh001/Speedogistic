package com.e.speedogistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.speedogistic.models.UserModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    EditText firstName,lastName,email,passwordet,phone,date,location;
    Spinner roleSpinner;
    Button signup;
    private ProgressDialog progressDialog;
    DatabaseReference reference;
    UserModel userModel;
    String UserId;
//    @Override
//    public void onBackPressed() {
//        //this is only needed if you have specific things
//        //that you want to do when the user presses the back button.
//        /* your specific things...*/
//        super.onBackPressed();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        signup=(Button)findViewById(R.id.registbutton);
        firstName=(EditText)findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        email=(EditText)findViewById(R.id.email);
        passwordet=(EditText)findViewById(R.id.passwordet);
        phone=(EditText)findViewById(R.id.phone);
        date=(EditText)findViewById(R.id.date);
        location=(EditText)findViewById(R.id.location);
        roleSpinner=(Spinner)findViewById(R.id.roleSpinner);
        //getSupportActionBar().setTitle("User Registration");
        ArrayList<String> list=new ArrayList<>();
        list.add("Client");
        list.add("Transporter");





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        roleSpinner.setAdapter(adapter);



       roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
              ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(firstName.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(lastName.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your phone!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(date.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your birth date!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordet.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your password!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(location.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your address!", Toast.LENGTH_SHORT).show();
                }
                else if (passwordet.getText().length() < 7) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 7 characters!", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setTitle("Registration User");
                    progressDialog.setMessage("Please wait while we create your account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);

                    progressDialog.show();



                   userModel=new UserModel(
                           firstName.getText().toString(),
                           lastName.getText().toString(),
                           email.getText().toString(),
                           passwordet.getText().toString(),
                           phone.getText().toString(),
                           date.getText().toString(),
                           location.getText().toString(),
                           roleSpinner.getSelectedItem().toString(),"",""
                           ,"","","","");

                    if (roleSpinner.getSelectedItem().equals("Client")){

                        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), passwordet.getText().toString())
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {


                                            UserId= mAuth.getCurrentUser().getUid();

                                            DocumentReference documentReference=firebaseFirestore.collection("Users").document(UserId);
                                            Map<String,Object> map=new HashMap<>();
                                            map.put("firstName",userModel.getFirstName());
                                            map.put("lastName",userModel.getLastName());
                                            map.put("email",userModel.getEmail().toLowerCase());
                                            map.put("password",userModel.getPassword());
                                            map.put("phone",userModel.getPhone());
                                            map.put("address",userModel.getAddress());
                                            map.put("date",userModel.getDate());
                                            map.put("role",userModel.getRole());







                                            documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Welcome To Speedogistics",Toast.LENGTH_LONG).show();
                                                    Intent intent=new Intent(getApplicationContext(),SignInPage.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                    progressDialog.dismiss();
                                                }
                                            });

                                        }

                                        else {
                                            progressDialog.hide();
                                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });

                    }else if (roleSpinner.getSelectedItem().equals("Transporter")){

                        Intent intent=new Intent(getApplicationContext(),SignUp1Activity.class);

                        intent.putExtra("userModel", userModel);
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();

                    }
                }
            }
        });


    }



}