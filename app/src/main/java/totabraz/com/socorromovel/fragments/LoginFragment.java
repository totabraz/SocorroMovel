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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import totabraz.com.socorromovel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private final String TAG = "Firebase";
    private FirebaseAuth mAuth;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private TextInputEditText edEmail;
    private TextInputEditText edPasswd;
    private LinearLayout llLoginArea;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Login");

        this.progressBar = view.findViewById(R.id.progressBar);
        this.mAuth = FirebaseAuth.getInstance();
        this.edEmail = view.findViewById(R.id.edtEmail);
        this.edPasswd = view.findViewById(R.id.edtPasswd);
        this.llLoginArea = view.findViewById(R.id.llLoginArea);
        final Button btnLogin = view.findViewById(R.id.btnSingIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Button btnSingUp = view.findViewById(R.id.btnSingUp);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp();
                hideKeyboard(btnLogin);
            }
        });

        this.edPasswd.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            login();
                            hideKeyboard(edPasswd);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        return  view;
    }


    private void login(){
        if ((edEmail.getText().toString().length()>0) &&(edPasswd.getText().toString().length()>0) ) {
            this.progressBar.setVisibility(View.VISIBLE);
            this.llLoginArea.setVisibility(View.GONE);
            String mail = edEmail.getText().toString();
            String pswd = edPasswd.getText().toString();
            setupFirebaseUser(mail, pswd);
        }
    }

    private void setupFirebaseUser(String mail, String pswd) {
        this.mAuth.signInWithEmailAndPassword(mail, pswd)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.flFragmentArea, ProfileFragment.newInstance());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else {
//                            task.getException().getCause();
//                            task.getException().getMessage();
                            Toast.makeText(getActivity().getApplicationContext(), "Erro ao Entrar",Toast.LENGTH_LONG).show();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });

        progressBar.setVisibility(View.GONE);
        llLoginArea.setVisibility(View.VISIBLE);

    }
    public void singUp(){
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragmentArea, SingUpFragment.newInstance());
        fragmentTransaction.addToBackStack("login");
        fragmentTransaction.commit();
    }


    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
