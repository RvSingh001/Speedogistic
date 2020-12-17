package com.e.speedogistic.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.e.speedogistic.R;
import com.e.speedogistic.SignInPage;
import com.e.speedogistic.UserMainActivity;
import com.e.speedogistic.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFrag extends Fragment {

    private TextView email;
    private EditText fname,lname,password,phone,address,dob;
    private Button update,delete;
    String Userid;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String Password;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        email=root.findViewById(R.id.pemail);
        fname=root.findViewById(R.id.pfirstName);
        lname  = root.findViewById(R.id.plastName);
        password =root.findViewById(R.id.ppasswordet);
        phone =root.findViewById(R.id.pphone);
        address =root.findViewById(R.id.plocation);
        dob =root.findViewById(R.id.pdate);
        update =root.findViewById(R.id.pupdate);
        delete=root.findViewById(R.id.pdelete);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getContext());
        preferences=getActivity().getSharedPreferences("Mypref",MODE_PRIVATE);
        editor=preferences.edit();
        init();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fname.getText())) {
                    Toast.makeText(getContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(lname.getText())) {
                    Toast.makeText(getContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText())) {
                    Toast.makeText(getContext(), "Enter your phone!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(dob.getText())) {
                    Toast.makeText(getContext(), "Enter your birth date!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(getContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(getContext(), "Enter your password!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText())) {
                    Toast.makeText(getContext(), "Enter your address!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().length() < 7) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 7 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Updating User profile");
                    progressDialog.setMessage("Please wait while we Update your account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);

                    progressDialog.show();

                    UserModel userModel = new UserModel(
                            fname.getText().toString(),
                            lname.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            phone.getText().toString(),
                            dob.getText().toString(),
                            address.getText().toString(),
                            "Client", "", ""
                            , "", "", "", "");

                    String userid = firebaseAuth.getCurrentUser().getUid();
                    if (!Password.equals(password.getText().toString())) {

                        firebaseAuth.getCurrentUser().updatePassword(password.getText().toString());
                        FirebaseAuth muth = FirebaseAuth.getInstance();
                        muth.signOut();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(getContext(), SignInPage.class));


                    }
                    DocumentReference documentReference = firebaseFirestore.collection("Users").document(userid);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("firstName", userModel.getFirstName());
                    map.put("lastName", userModel.getLastName());
                    map.put("email", userModel.getEmail());
                    map.put("password", userModel.getPassword());
                    map.put("phone", userModel.getPhone());
                    map.put("address", userModel.getAddress());
                    map.put("date", userModel.getDate());
                    map.put("role", userModel.getRole());
                    documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getContext(), UserMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Deleting User profile");
                progressDialog.setMessage("Please wait while we Delete your account");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);

                progressDialog.show();
                String userid=firebaseAuth.getCurrentUser().getUid();
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                firebaseUser.delete();
                firebaseFirestore.collection("Users").document(userid).delete();
                Toast.makeText(getContext(),"User Profile Deleted",Toast.LENGTH_LONG).show();
                FirebaseAuth muth=FirebaseAuth.getInstance();
                muth.signOut();
                editor.clear();
                editor.commit();
                progressDialog.dismiss();
                Intent intent=new Intent(getContext(),SignInPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }
    public void init()
    {

            Userid = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference=firebaseFirestore.collection("Users").document(Userid);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    Log.e("error",documentSnapshot.getData().toString());
                    Map<String,Object> newMap =(Map<String, Object>) documentSnapshot.getData();



                            email .setText(newMap.get("email").toString());
                            fname.setText(newMap.get("firstName").toString());
                            lname.setText(newMap.get("lastName").toString());
                            password.setText(newMap.get("password").toString());
                            phone.setText(newMap.get("phone").toString());
                            address.setText(newMap.get("address").toString());
                            dob.setText(newMap.get("date").toString());
                            Password=newMap.get("password").toString();


                }
            });


        }

}