package com.e.speedogistic.ui.transProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.speedogistic.IntroActivity;
import com.e.speedogistic.R;
import com.e.speedogistic.SignInPage;
import com.e.speedogistic.TransActivity;
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


public class TransProfileFrag extends Fragment {
    private TextView email;
    private EditText fname,lname,password,phone,address,dob,comname,type,load,vehnum,licnum,rate;
    private Button update,delete;
    String Userid;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String Password;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trans_profile, container, false);

        email=root.findViewById(R.id.tpemail);
        fname=root.findViewById(R.id.tpfirstName);
        lname  = root.findViewById(R.id.tplastName);
        password =root.findViewById(R.id.tppassword);
        phone =root.findViewById(R.id.tpphone);
        address =root.findViewById(R.id.tplocation);
        dob =root.findViewById(R.id.tpdate);


                comname=root.findViewById(R.id.tpcomname);
                type=root.findViewById(R.id.tptype);
                load=root.findViewById(R.id.tpcapacite);
                vehnum=root.findViewById(R.id.tpVehiclenum);
                licnum=root.findViewById(R.id.tpLicensenum);
                rate=root.findViewById(R.id.tprate);

        update =root.findViewById(R.id.tppupdate);
        delete=root.findViewById(R.id.tpdelete);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
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

                } else if (TextUtils.isEmpty(comname.getText())) {
                    Toast.makeText(getContext(), "Enter your logistics Name!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(type.getText())) {
                    Toast.makeText(getContext(), "Enter your Type of Vehicle!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(load.getText())) {
                    Toast.makeText(getContext(), "Enter your Max Load Of Vehicle!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(vehnum.getText())) {
                    Toast.makeText(getContext(), "Enter your Vehicle number!", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(licnum.getText())) {
                    Toast.makeText(getContext(), "Enter your License number!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(rate.getText())) {
                    Toast.makeText(getContext(), "Enter Rate Per KM!", Toast.LENGTH_SHORT).show();

                }else if (password.getText().length() < 7) {
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
                            "Transporter", comname.getText().toString(), type.getText().toString()
                            , load.getText().toString(), vehnum.getText().toString(), licnum.getText().toString(), rate.getText().toString());

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
                    map.put("comname", userModel.getComname());
                    map.put("typeOfVehicule", userModel.getTypeOfVehicule());
                    map.put("capacity", userModel.getCapacity());
                    map.put("regnum", userModel.getRegnum());
                    map.put("licenum", userModel.getLicenum());
                    map.put("rate", userModel.getRate());

                    documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getContext(), TransActivity.class);
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

                comname.setText(newMap.get("comname").toString());
                type.setText(newMap.get("typeOfVehicule").toString());
                load.setText(newMap.get("capacity").toString());
                vehnum.setText(newMap.get("regnum").toString());
                licnum.setText(newMap.get("licenum").toString());
             rate.setText(newMap.get("rate").toString());
            }
        });


    }

}