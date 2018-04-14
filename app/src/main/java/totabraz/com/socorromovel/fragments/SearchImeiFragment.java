package totabraz.com.socorromovel.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.domain.Smartphone;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchImeiFragment extends Fragment {

    private TextInputEditText edImei;
    private TextView tvImei;
    private TextView tvStatus;
    private TextView tvOwner;
    private TextView tvDateAdd;
    private TextView tvDateReported;
    private ProgressBar progressBar;
    private RelativeLayout reportArea;

    public SearchImeiFragment() {
        // Required empty public constructor
    }


    public static SearchImeiFragment newInstance() {
        SearchImeiFragment fragment = new SearchImeiFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_imei, container, false);
        edImei = view.findViewById(R.id.edImei);
        edImei.setText("");
        edImei.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (edImei.getText().toString().length()>1)
                                checkImei(edImei.getText().toString());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        tvImei = view.findViewById(R.id.tvImei);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvOwner = view.findViewById(R.id.tvOwner);
        tvDateAdd = view.findViewById(R.id.tvDateAdd);
        tvDateReported = view.findViewById(R.id.tvDateReported);
        progressBar = view.findViewById(R.id.progressBar);
        reportArea = view.findViewById(R.id.reportArea);

        Button btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edImei.getText().toString().length()>1)
                    checkImei(edImei.getText().toString());
            }
        });
        return view;
    }

    private void checkImei(final String imei) {
        progressBar.setVisibility(View.VISIBLE);
        reportArea.setVisibility(View.GONE);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_REPORTED_PHONES).child(imei);
        ValueEventListener imeiNumberListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Smartphone smartphone = dataSnapshot.getValue(Smartphone.class);
                applyResult(smartphone, imei);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
        mDatabase.addValueEventListener(imeiNumberListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        edImei.setText("");
        this.reportArea.setVisibility(View.GONE);
    }

    public void applyResult(Smartphone smartphone, String imei) {
        this.progressBar.setVisibility(View.GONE);
        reportArea.setVisibility(View.VISIBLE);

        if (smartphone == null) {
            this.tvImei.setText("IMEI: " + imei);
            this.tvStatus.setText("IMEI não encontrado");
            this.tvStatus.setBackgroundColor(getResources().getColor(R.color.statusNotFound));
            this.tvOwner.setText("");
            this.tvDateAdd.setText("");
            this.tvDateReported.setText("");

        } else if (smartphone.getStatus().equals(SysUtils.PHONE_STOLED)) {
            this.tvImei.setText("IMEI: " + smartphone.getImei());
            this.tvStatus.setText(smartphone.getStatus());
            this.tvStatus.setBackgroundColor(getResources().getColor(R.color.statusStoled));
            this.tvOwner.setText("Proprietário: " + smartphone.getOwner());
            this.tvDateAdd.setText("Data de cadastro: " + smartphone.getDateAdd());
            this.tvDateReported.setText("Dia do roubo: " + smartphone.getDateReported());

        } else if (smartphone.getStatus().equals(SysUtils.PHONE_ADD)) {
            this.tvImei.setText("IMEI: " + smartphone.getImei());
            this.tvStatus.setText(smartphone.getStatus());
            this.tvStatus.setBackgroundColor(getResources().getColor(R.color.statusAdd));
            this.tvOwner.setText("Proprietário: " + smartphone.getOwner());
            this.tvDateAdd.setText("Data de cadastro: " + smartphone.getDateAdd());
            this.tvDateReported.setText("");
        }
    }

}
