package totabraz.com.socorromovel.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.AddPhoneActivity;
import totabraz.com.socorromovel.activities.MainActivity;
import totabraz.com.socorromovel.adapters.MyPhoneAdapter;
import totabraz.com.socorromovel.domain.Smartphone;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ProgressBar progressBar ;
    private RecyclerView rvMyPhones ;
    private Button btnReportMobile ;
    private TextView tvNothingToShow;
    private ArrayList<Smartphone> myPhones;

    private final String TAG = "MyProfileFragment";
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Meu Perfil");
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rvMyPhones = view.findViewById(R.id.rvMyPhones);
        btnReportMobile = view.findViewById(R.id.btnReportMobile);
        Button btnAddMobile = view.findViewById(R.id.btnAddMobile);
        tvNothingToShow = view.findViewById(R.id.tvNothingToShow);
        mAuth = FirebaseAuth.getInstance();
        getMyPhones();
        btnAddMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddPhoneActivity.class));
            }
        });
        return view;
    }

    private void getMyPhones() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_USERS).child(mAuth.getCurrentUser().getUid()).child(SysUtils.FB_MY_PHONES);
        ValueEventListener todoListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPhones = new ArrayList<>();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Smartphone smartphone = objSnapshot.getValue(Smartphone.class);
                    myPhones.add(smartphone);
                }
                if(myPhones.size() < 1) {
                    tvNothingToShow.setVisibility(View.VISIBLE);
                    rvMyPhones.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    tvNothingToShow.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    rvMyPhones.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    MyPhoneAdapter mAdapter = new MyPhoneAdapter(getActivity(), myPhones);
                    rvMyPhones.setLayoutManager(mLayoutManager);
                    rvMyPhones.setItemAnimator(new DefaultItemAnimator());
                    rvMyPhones.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
        mDatabase.addValueEventListener(todoListener);
    }

}

