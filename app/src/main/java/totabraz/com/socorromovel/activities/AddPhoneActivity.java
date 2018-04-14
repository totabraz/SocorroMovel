package totabraz.com.socorromovel.activities;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.domain.Smartphone;
import totabraz.com.socorromovel.utils.SysUtils;

public class AddPhoneActivity extends AppCompatActivity {


    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private FirebaseAuth mAuth;

    private TextInputEditText edImei;
    private TextInputEditText edDate;
    private TextInputEditText edModel;
    private Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        getSupportActionBar().setTitle("Adicionar Celular");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        /** ---- Preset. DataPickerDialog --- */
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showCalendar(year, monthOfYear, dayOfMonth);
            }
        };

        edImei = findViewById(R.id.edImei);
        edModel = findViewById(R.id.edModel);
        edDate = findViewById(R.id.edDate);
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddPhoneActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validFields()){
                    Smartphone smartphone = new Smartphone();
                    smartphone.setDateAdd(edDate.getText().toString());
                    smartphone.setImei(edImei.getText().toString().replaceAll("\\s+",""));
                    smartphone.setOwner(mAuth.getCurrentUser().getUid());
                    smartphone.setStatus(SysUtils.PHONE_ADD);
                    addPhoneOnFirebase(smartphone);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean validFields() {
        return
                edImei.getText().toString().length() > 5 &&
                        edDate.getText().toString().length() > 1 &&
                        edModel.getText().toString().length() > 5;

    }

    private void showCalendar(int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateFieldDate();
    }

    private void updateFieldDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        edDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void checkImei(final Smartphone smartphone) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_REPORTED_PHONES).child(smartphone.getImei());
        final ValueEventListener imeiNumberListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Smartphone fbPhone = dataSnapshot.getValue(Smartphone.class);
                if (fbPhone.getStatus().equals(SysUtils.PHONE_STOLED)) {
                    Toast.makeText(getApplicationContext(), "Estatus deste IMEI está como: " + SysUtils.PHONE_STOLED, Toast.LENGTH_LONG).show();
                } else {

                }
                addPhoneOnFirebase(smartphone);
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
    private void addPhoneOnFirebase(Smartphone smartphone){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.FB_USERS).child(mAuth.getCurrentUser().getUid()).child(SysUtils.FB_MY_PHONES).child(smartphone.getImei());
        mDatabase.setValue(smartphone);
        cleanFild();
        Toast.makeText(getApplicationContext(), "Cadastrado" ,Toast.LENGTH_SHORT).show();
        finish();

    }

    private void cleanFild() {
        edDate.setText("");
        edImei.setText("");
        edModel.setText("");
    }
}
