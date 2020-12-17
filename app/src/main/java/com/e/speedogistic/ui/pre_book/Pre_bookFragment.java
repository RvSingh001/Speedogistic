package com.e.speedogistic.ui.pre_book;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.speedogistic.Booking;
import com.e.speedogistic.R;
import com.e.speedogistic.models.BookModel;
import com.e.speedogistic.models.UserModel;
import com.e.speedogistic.ui.feedback.FeedBackFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Pre_bookFragment extends Fragment {
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
        View root = inflater.inflate(R.layout.fragment_pre_book, container, false);
        recyclerView = root.findViewById(R.id.precycleview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        preferences=getActivity().getSharedPreferences("logemail", Context.MODE_PRIVATE);
        preferences1=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final Query query = firebaseFirestore.collection("Bookings").whereEqualTo("useremail",preferences1.getString("email", ""));
        final FirestoreRecyclerOptions<Pre_bookViewModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Pre_bookViewModel>().setQuery(query, Pre_bookViewModel.class)
                .build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Pre_bookViewModel, Pre_bookFragment.Pre_bookViewModelViewHolder>(firestoreRecyclerOptions) {
            @NonNull
            @Override
            public Pre_bookFragment.Pre_bookViewModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookitem, parent, false);


                return new Pre_bookViewModelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final Pre_bookFragment.Pre_bookViewModelViewHolder Pre_bookViewModelHolder, int i, @NonNull final Pre_bookViewModel pre_bookViewModel) {
                Pre_bookViewModelHolder.bookid.setText(pre_bookViewModel.getBookid());
                        Pre_bookViewModelHolder.comname.setText(pre_bookViewModel.getComname());
                        Pre_bookViewModelHolder.typeof.setText(pre_bookViewModel.getTypeof());
                        Pre_bookViewModelHolder.load.setText(pre_bookViewModel.getLoad());
                        Pre_bookViewModelHolder.vehnum.setText(pre_bookViewModel.getVehnum());
                        Pre_bookViewModelHolder.licnum.setText(pre_bookViewModel.getLicnum());
                        Pre_bookViewModelHolder.rate.setText(pre_bookViewModel.getRate());
                        Pre_bookViewModelHolder.drivername.setText(pre_bookViewModel.getDrivername());
                        Pre_bookViewModelHolder.logphone.setText(pre_bookViewModel.getLogphone());
                        Pre_bookViewModelHolder.logemail.setText(pre_bookViewModel.getLogemail());
                        Pre_bookViewModelHolder.logaddress.setText(pre_bookViewModel.getLogaddress());
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
                                String email=pre_bookViewModel.getLogemail();
                                FeedBackFragment feedBackFragment = new FeedBackFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                Bundle result = new Bundle();
                                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(),feedBackFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                editor.putString("logemail",pre_bookViewModel.getLogemail());
                                editor.commit();
                                ProgressDialog progressDialog=new ProgressDialog(getContext());
                                progressDialog.setTitle("wait");
                                progressDialog.setMessage("Please Wait..");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                progressDialog.dismiss();
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

        private TextView bookid, comname, typeof, load, vehnum, licnum, rate, drivername, logphone, logemail, logaddress, pickaddress, destination,currentdate, pickdate, picktime, username, useremail, usernum, useraddress, bookingstatus, estimate, totalcost, message;
        private Button submit;

        public Pre_bookViewModelViewHolder(@NonNull View itemView) {

            super(itemView);
            bookid = itemView.findViewById(R.id.pbookid);
            comname = itemView.findViewById(R.id.pcomname);
            typeof = itemView.findViewById(R.id.ptype);
            load = itemView.findViewById(R.id.pload);
            vehnum = itemView.findViewById(R.id.pRegnum);
            licnum = itemView.findViewById(R.id.plicnum);
            rate = itemView.findViewById(R.id.prate);
            drivername = itemView.findViewById(R.id.pname);
            logphone = itemView.findViewById(R.id.pphone);
            logemail = itemView.findViewById(R.id.pemail);
            logaddress = itemView.findViewById(R.id.paddress);

            pickaddress = itemView.findViewById(R.id.ppickup);
            destination = itemView.findViewById(R.id.pdes);
            currentdate = itemView.findViewById(R.id.tcurrentdate);
            pickdate = itemView.findViewById(R.id.pdate);
            picktime = itemView.findViewById(R.id.ppicktime);
            username = itemView.findViewById(R.id.pusername);
            useremail = itemView.findViewById(R.id.puseremail);
            usernum = itemView.findViewById(R.id.pusernum);
            useraddress = itemView.findViewById(R.id.puseraddress);
            bookingstatus = itemView.findViewById(R.id.pbookingstatus);
            estimate = itemView.findViewById(R.id.pestimate);
            totalcost = itemView.findViewById(R.id.ptotalcost);
            message = itemView.findViewById(R.id.pmessage);
            submit = itemView.findViewById(R.id.pfeedback);
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