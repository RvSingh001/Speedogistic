package com.e.speedogistic.ui.Transporters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.e.speedogistic.Booking;
import com.e.speedogistic.R;
import com.e.speedogistic.models.BookModel;
import com.e.speedogistic.ui.feedback.FeedBackFragment;
import com.e.speedogistic.ui.pre_book.Pre_bookFragment;
import com.e.speedogistic.ui.pre_book.Pre_bookViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class TransFragment extends Fragment {


    public static String email;
    private TransViewModel transViewModel;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    FirebaseAuth firebaseAuth;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SharedPreferences preferences1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trans, container, false);
        recyclerView = root.findViewById(R.id.recycleview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        preferences=getActivity().getSharedPreferences("logemail", Context.MODE_PRIVATE);
        preferences1=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final Query query = firebaseFirestore.collection("Users").whereEqualTo("role","Transporter");
        final FirestoreRecyclerOptions<TransViewModel> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<TransViewModel>().setQuery(query, TransViewModel.class)
                .build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<TransViewModel, TransFragment.TransViewModelViewHolder>(firestoreRecyclerOptions) {
            @NonNull
            @Override
            public TransFragment.TransViewModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transitem, parent, false);


                return new TransFragment.TransViewModelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final TransFragment.TransViewModelViewHolder transViewModelViewHolder, int i, @NonNull final TransViewModel transViewModel) {
                transViewModelViewHolder.name.setText(transViewModel.getFirstName()+" "+transViewModel.getLastName());
                transViewModelViewHolder.email.setText(transViewModel.getEmail());
                transViewModelViewHolder.phone.setText(transViewModel.getPhone());
                transViewModelViewHolder.address.setText(transViewModel.getAddress());
                transViewModelViewHolder.comnames.setText(transViewModel.getComname());
                transViewModelViewHolder.type.setText("Type Of Vehicle : "+transViewModel.getTypeOfVehicule());
                transViewModelViewHolder.load.setText("Max Load : "+transViewModel.getCapacity());
                transViewModelViewHolder.resnum.setText("Registration Number : "+transViewModel.getRegnum());
                transViewModelViewHolder.licnum.setText("License Number : "+transViewModel.getLicenum());
                transViewModelViewHolder.ratekm.setText("Rate : "+transViewModel.getRate()+"/Km");

                transViewModelViewHolder.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookModel bookModel=new BookModel(transViewModel.getFirstName()+" "+transViewModel.getLastName(),
                                transViewModel.getEmail(),
                                transViewModel.getPhone(),transViewModel.getAddress(),
                                transViewModel.getComname(),transViewModel.getTypeOfVehicule(),
                                transViewModel.getCapacity(),transViewModel.getRegnum(),
                                transViewModel.getLicenum(),transViewModel.getRate());
                        ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setTitle("wait");
                        progressDialog.setMessage("Please Wait..");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        Intent intent=new Intent(getContext(), Booking.class);
                        intent.putExtra("bookmodel",bookModel);
                        startActivity(intent);
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

    private class TransViewModelViewHolder extends RecyclerView.ViewHolder {

        private TextView  name,email,phone,address,comnames,type,load,resnum,licnum,ratekm;
        private Button submit;

        public TransViewModelViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.tname);
            email = itemView.findViewById(R.id.temail);
            phone = itemView.findViewById(R.id.tphone);
            address = itemView.findViewById(R.id.tlocation);
            comnames = itemView.findViewById(R.id.tcomname);
            type = itemView.findViewById(R.id.ttype);
            load = itemView.findViewById(R.id.tcapacite);
            resnum = itemView.findViewById(R.id.tVehiclenum);
            licnum = itemView.findViewById(R.id.tLicensenum);
            ratekm = itemView.findViewById(R.id.trate);

            submit = itemView.findViewById(R.id.tbook);
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