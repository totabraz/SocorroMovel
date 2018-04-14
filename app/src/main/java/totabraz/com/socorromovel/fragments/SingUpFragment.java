package totabraz.com.socorromovel.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.MainActivity;
import totabraz.com.socorromovel.domain.User;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpFragment extends Fragment {

    private final String TAG = "SingUp";

    private FirebaseAuth mAuth;

    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPhoneNumber;
    private TextInputEditText edPasswdA;
    private TextInputEditText edPasswdB;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public SingUpFragment() {
        // Required empty public constructor
    }


    public static SingUpFragment newInstance() {
        SingUpFragment fragment = new SingUpFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sing_up, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Novo cadastro");
        this.mAuth = FirebaseAuth.getInstance();
        edName = view.findViewById(R.id.edName);
        edEmail = view.findViewById(R.id.edEmail);
        edPhoneNumber = view.findViewById(R.id.edPhoneNumber);
        edPasswdA = view.findViewById(R.id.edPasswdA);
        edPasswdB = view.findViewById(R.id.edPasswdB);
        Button btnSingUp = view.findViewById(R.id.btnSingUp);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validFields() && validPassWd()) {
                    createFirebaseUser(createUser(), edPasswdA.getText().toString());
                }
            }
        });
        edPasswdB.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (validFields() && validPassWd()) {
                                createFirebaseUser(createUser(), edPasswdA.getText().toString());
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        return view;
    }
    private User createUser(){
        User user = new User();
        user.setName(edName.getText().toString());
        user.setEmail(edEmail.getText().toString());
        user.setPhoneNumber(edPhoneNumber.getText().toString());
        return user;
    }
    private boolean validFields(){
        return edName.getText().toString().length() > 1 &&
                edEmail.getText().toString().length() > 1 &&
                edPhoneNumber.getText().toString().length() > 1 &&
                edPasswdA.getText().toString().length() > 1 &&
                edPasswdB.getText().toString().length() > 1;
    }

    private boolean validPassWd(){
        return edPasswdA.getText().toString().equals(edPasswdB.getText().toString());

    }
    private void createFirebaseUser(final User user, String pswd) {
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), pswd)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            addUser(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }

                });
    }
    private void addUser(User user){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_USERS).child(mAuth.getCurrentUser().getUid());
        mDatabase.setValue(user);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragmentArea, ProfileFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
