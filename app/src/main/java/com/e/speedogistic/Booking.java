package com.e.speedogistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.DateValueSanitizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.speedogistic.models.BookModel;
import com.e.speedogistic.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firestore.v1.DocumentRemove;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity {
    BookModel bookModel;

    private TextView comname,typeof,load,vehnum,licnum,rate,drivername,phone,email,Address;
    EditText pickaddress,Des,pickdate, picktime;
    String username,useremail,usernum,useraddress;
    Button submit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking1);

                comname=findViewById(R.id.bcomname);
                typeof=findViewById(R.id.btype);
                load=findViewById(R.id.bload);
                vehnum=findViewById(R.id.bRegnum);
                licnum=findViewById(R.id.blicnum);
                rate=findViewById(R.id.brate);
                drivername=findViewById(R.id.bname);
                phone=findViewById(R.id.bphone);
                email=findViewById(R.id.bemail);
                Address=findViewById(R.id.baddress);
                pickaddress=findViewById(R.id.bpickup);
                Des=findViewById(R.id.bdes);
                pickdate=findViewById(R.id.bdate);
                picktime=findViewById(R.id.btime);
                submit=findViewById(R.id.bcontinue);
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseFirestore= FirebaseFirestore.getInstance();
                progressDialog=new ProgressDialog(this);

                bookModel=(BookModel) getIntent().getExtras().getSerializable("bookmodel");
                getSupportActionBar().setTitle("Bookings");
                comname.setText(bookModel.getComnames());
                typeof.setText("Type Of Vehicle"+bookModel.getType());
                load.setText("Max Load : "+bookModel.getLoad());
                vehnum.setText("Registration Number : "+bookModel.getResnum());
                licnum.setText("License Number : "+bookModel.getLicnum());
                rate.setText("Rate : "+bookModel.getRatekm()+"/Km");
                drivername.setText(bookModel.getName());
                phone.setText(bookModel.getPhone());
                email.setText(bookModel.getEmail());
                Address.setText(bookModel.getAddress());

                String Userid=firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference=firebaseFirestore.collection("Users").document(Userid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                Log.e("error", documentSnapshot.getData().toString());
                Map<String, Object> newMap = (Map<String, Object>) documentSnapshot.getData();
                        username=newMap.get("firstName").toString()+" "+newMap.get("lastName").toString();
                        useremail=newMap.get("email").toString();
                        usernum=newMap.get("phone").toString();
                        useraddress=newMap.get("address").toString();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pickaddress.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter Pick Up Address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Des.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter Destination Addresse", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pickdate.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter Pick Up Date", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(picktime.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter Pick Up Time", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Booking");
                    progressDialog.setMessage("Please Wait your Booking is submititng");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    DocumentReference documentReference = firebaseFirestore.collection("Bookings").document();
                    Map<String, Object> map = new HashMap<String, Object>();
                    Date date = new Date();
                    String bookid = String.valueOf(date.getDate()) + "" + String.valueOf(date.getMonth())
                            + "" + String.valueOf(date.getMonth())
                            + "" + String.valueOf(date.getYear())
                            + "" + String.valueOf(date.getHours())
                            + "" + String.valueOf(date.getMinutes())
                            + "" + String.valueOf(date.getSeconds());
                    //+""+String.valueOf(date.getTime());
                    map.put("bookid", bookid);
                    map.put("comname", comname.getText().toString());
                    map.put("typeof", typeof.getText().toString());
                    map.put("load", load.getText().toString());
                    map.put("vehnum", vehnum.getText().toString());
                    map.put("licnum", licnum.getText().toString());
                    map.put("rate", rate.getText().toString());
                    map.put("drivername", drivername.getText().toString());
                    map.put("logphone", phone.getText().toString());
                    map.put("logemail", email.getText().toString());
                    map.put("logaddress", Address.getText().toString());
                    map.put("pickaddress", pickaddress.getText().toString());
                    map.put("destination", Des.getText().toString());
                    map.put("currentdate",String.valueOf(date.getDate())+"/"+String.valueOf(date.getMonth())+"/"+String.valueOf(date.getYear()) );
                    map.put("pickdate", pickdate.getText().toString());
                    map.put("picktime", picktime.getText().toString());
                    map.put("username", username);
                    map.put("useremail", useremail);
                    map.put("usernum", usernum);
                    map.put("useraddress", useraddress);
                    map.put("bookingstatus", "Wating for logistics confirmation");
                    map.put("estimate", "Being Processed");
                    map.put("totalcost", "Being Processed");
                    map.put("message", "Thank you for using our service");

                    documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Thank you to Choose Us For Service", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });
    }
}