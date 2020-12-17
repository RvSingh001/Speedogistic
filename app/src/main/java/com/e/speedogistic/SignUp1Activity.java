package com.e.speedogistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.speedogistic.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class SignUp1Activity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    UserModel userModel;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    EditText type,capacity,ResNum,LicNum,rate,comname;
    private Button signup;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        firebaseFirestore= FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        ResNum=findViewById(R.id.Vehiclenum);
        LicNum=findViewById(R.id.Licensenum);
        type=(EditText)findViewById(R.id.type);
        capacity=(EditText)findViewById(R.id.capacite);
        rate=findViewById(R.id.rate);
        comname=findViewById(R.id.comname);
        signup=(Button)findViewById(R.id.registbutton1);
        mAuth = FirebaseAuth.getInstance();
      //  getSupportActionBar().setTitle("Transporter Registration");
        userModel=(UserModel) getIntent().getExtras().getSerializable("userModel");



        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(comname.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your Logistics Service Name !", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(type.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your vehicle Type !", Toast.LENGTH_SHORT).show();
                }
                     else if (TextUtils.isEmpty(capacity.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your vehicle capacity !", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ResNum.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your vehicle Registration Number !", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(LicNum.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter your vehicle License Number !", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(rate.getText())) {
                                    Toast.makeText( getApplicationContext(),"Enter your Rate Per Km !", Toast.LENGTH_SHORT).show();
                }else {
                    userModel.setComname(comname.getText().toString());
                    userModel.setTypeOfVehicule(type.getText().toString());
                    userModel.setCapacity(capacity.getText().toString());
                    userModel.setRegnum(ResNum.getText().toString());
                    userModel.setLicenum(LicNum.getText().toString());
                    userModel.setRate(rate.getText().toString());


                    progressDialog.setTitle("Registration User");
                    progressDialog.setMessage("Please wait while we create your account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
                    progressDialog.show();

                    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

                    mAuth.createUserWithEmailAndPassword(userModel.getEmail(),userModel.getPassword())
                            .addOnCompleteListener(SignUp1Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        UserId= mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference=firebaseFirestore.collection("Users").document(UserId);
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("firstName",userModel.getFirstName());
                                        map.put("lastName",userModel.getLastName());
                                        map.put("email",userModel.getEmail());
                                        map.put("password",userModel.getPassword());
                                        map.put("phone",userModel.getPhone());
                                        map.put("address",userModel.getAddress());
                                        map.put("date",userModel.getDate());
                                        map.put("role",userModel.getRole());

                                        map.put("comname",userModel.getComname());
                                        map.put("typeOfVehicule",userModel.getTypeOfVehicule());
                                        map.put("capacity",userModel.getCapacity()+" "+"KG");
                                        map.put("regnum",userModel.getRegnum());
                                        map.put("licenum",userModel.getLicenum());
                                        map.put("rate",userModel.getRate());




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

                }
            }
        });

    }
}