package totabraz.com.socorromovel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.adapters.ListCallsAdapter;
import totabraz.com.socorromovel.controller.MyController;
import totabraz.com.socorromovel.domain.EmergencyNumber;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 */
public class ListCallsFragment extends Fragment {

    private MyController myController;
    private ArrayList<EmergencyNumber> numbers;
    private ProgressBar progressBar;

    private RecyclerView rvListCalls;
    private ListCallsAdapter mAdapter;


    public ListCallsFragment() {
        myController = new MyController();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_calls, container, false);
        rvListCalls = view.findViewById(R.id.rvListCalls);
        progressBar = view.findViewById(R.id.progressBar);
        this.getEmergencyCalls();
        return view;
    }

    private void getEmergencyCalls() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_EMERGENCY_NUMBERS);
        ValueEventListener emergencyNumbersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numbers = new ArrayList<>();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    numbers.add(objSnapshot.getValue(EmergencyNumber.class));
                }
                if (numbers.size() < 1) {
//                    tvNothing.setVisibility(View.VISIBLE);
                    rvListCalls.setVisibility(View.GONE);
                } else {
//                    tvNothing.setVisibility(View.GONE);
                    rvListCalls.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                    mAdapter = new ListCallsAdapter(getActivity(), numbers);
                    rvListCalls.setLayoutManager(mLayoutManager);
                    rvListCalls.setItemAnimator(new DefaultItemAnimator());
                    rvListCalls.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
        mDatabase.addValueEventListener(emergencyNumbersListener);
    }


}
