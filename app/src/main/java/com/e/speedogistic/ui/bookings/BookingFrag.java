package com.e.speedogistic.ui.bookings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.speedogistic.R;

import com.e.speedogistic.ui.pre_book.Pre_bookViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class BookingFrag extends Fragment {

    public static String email;
    private Pre_bookViewModel prebookViewModel;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    FirebaseAuth firebaseAuth;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SharedPreferences preferences1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        recyclerView = root.findViewById(R.id.tprecycleview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        preferences = getActivity().getSharedPreferences("logemail", Context.MODE_PRIVATE);
        preferences1 = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        final Query query = firebaseFirestore.collection("Bookings").whereEqualTo("logemail",preferences1.getString("email",""));
        final FirestoreRecyclerOptions<Pre_bookViewModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Pre_bookViewModel>().setQuery(query, Pre_bookViewModel.class)
                .build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Pre_bookViewModel, BookingFrag.Pre_bookViewModelViewHolder>(firestoreRecyclerOptions) {
            @NonNull
            @Override
            public BookingFrag.Pre_bookViewModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transbook, parent, false);


                return new BookingFrag.Pre_bookViewModelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final BookingFrag.Pre_bookViewModelViewHolder Pre_bookViewModelHolder, final int i, @NonNull final Pre_bookViewModel pre_bookViewModel) {
                final String docid=getSnapshots().getSnapshot(i).getId();

                Pre_bookViewModelHolder.bookid.setText(pre_bookViewModel.getBookid());
                Pre_bookViewModelHolder.comname.setText(pre_bookViewModel.getComname());
                Pre_bookViewModelHolder.typeof.setText(pre_bookViewModel.getTypeof());
                Pre_bookViewModelHolder.load.setText(pre_bookViewModel.getLoad());
                Pre_bookViewModelHolder.vehnum.setText(pre_bookViewModel.getVehnum());
                Pre_bookViewModelHolder.licnum.setText(pre_bookViewModel.getLicnum());
                Pre_bookViewModelHolder.rate.setText(pre_bookViewModel.getRate());
                Pre_bookViewModelHolder.drivername.setText(pre_bookViewModel.getDrivername());
                Pre_bookViewModelHolder.pickaddress.setText(pre_bookViewModel.getPickaddress());
                Pre_bookViewModelHolder.destination.setText(pre_bookViewModel.getDestination());
                Pre_bookViewModelHolder.currentdate.setText(pre_bookViewModel.getCurrentdate());
                Pre_bookViewModelHolder.pickdate.setText(pre_bookViewModel.getPickdate());
                Pre_bookViewModelHolder.picktime.setText(pre_bookViewModel.getPicktime());
                Pre_bookViewModelHolder.username.setText(pre_bookViewModel.getUsername());
                Pre_bookViewModelHolder.useremail.setText(pre_bookViewModel.getUseremail());
                Pre_bookViewModelHolder.usernum.setText(pre_bookViewModel.getUsernum());
                Pre_bookViewModelHolder.useraddress.setText(pre_bookViewModel.getUseraddress());
                Pre_bookViewModelHolder.bookingstatus.setText(pre_bookViewModel.getBookingstatus());
                Pre_bookViewModelHolder.estimate.setText(pre_bookViewModel.getEstimate());
                Pre_bookViewModelHolder.totalcost.setText(pre_bookViewModel.getTotalcost());
                Pre_bookViewModelHolder.message.setText(pre_bookViewModel.getMessage());
                Pre_bookViewModelHolder.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                       DocumentReference documentReference= firebaseFirestore.collection("Bookings").document(docid);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("bookingstatus", Pre_bookViewModelHolder.bookingstatus.getText().toString());
                        map.put("estimate", Pre_bookViewModelHolder.estimate.getText().toString());
                        map.put("totalcost", Pre_bookViewModelHolder.totalcost.getText().toString());
                        map.put("message", Pre_bookViewModelHolder.message.getText().toString());
                        documentReference.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(),"Successfully Update",Toast.LENGTH_LONG).show();
                                Fragment fragment=getActivity().getSupportFragmentManager().findFragmentById(((ViewGroup)getView().getParent()).getId()
                                );
                                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                fragmentTransaction.detach(fragment);
                                fragmentTransaction.attach(fragment);
                                fragmentTransaction.commit();
                            }
                        });
                    }
                });
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(firestoreRecyclerAdapter);
        return root;
    }


    private class Pre_bookViewModelViewHolder extends RecyclerView.ViewHolder {

        private TextView bookid, comname, typeof, load, vehnum, licnum, rate, drivername, pickaddress, destination, currentdate,pickdate, picktime, username, useremail, usernum, useraddress;

       EditText bookingstatus, estimate, totalcost, message;
        private Button submit;

        public Pre_bookViewModelViewHolder(@NonNull View itemView) {

            super(itemView);
            bookid = itemView.findViewById(R.id.tpbookid);
            comname = itemView.findViewById(R.id.tpcomname);
            typeof = itemView.findViewById(R.id.tptype);
            load = itemView.findViewById(R.id.tpload);
            vehnum = itemView.findViewById(R.id.tpRegnum);
            licnum = itemView.findViewById(R.id.tplicnum);
            rate = itemView.findViewById(R.id.tprate);
            drivername = itemView.findViewById(R.id.tpname);
            pickaddress = itemView.findViewById(R.id.tppickup);
            destination = itemView.findViewById(R.id.tpdes);
            currentdate=itemView.findViewById(R.id.tpcurrentdate);
            pickdate = itemView.findViewById(R.id.tpdate);
            picktime = itemView.findViewById(R.id.tppicktime);
            username = itemView.findViewById(R.id.tpusername);
            useremail = itemView.findViewById(R.id.tpuseremail);
            usernum = itemView.findViewById(R.id.tpusernum);
            useraddress = itemView.findViewById(R.id.tpuseraddress);
            bookingstatus = itemView.findViewById(R.id.tpbookingstatus);
            estimate = itemView.findViewById(R.id.tpestimate);
            totalcost = itemView.findViewById(R.id.tptotalcost);
            message = itemView.findViewById(R.id.tpmessage);
            submit = itemView.findViewById(R.id.tpupdate);
        }

    }

    public void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.startListening();
    }

    public void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }
}