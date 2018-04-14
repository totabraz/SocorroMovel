package totabraz.com.socorromovel.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.informations.InformationActivity;
import totabraz.com.socorromovel.domain.AnonymousReport;
import totabraz.com.socorromovel.utils.SysUtils;
import totabraz.com.socorromovel.fragments.TimePickerFragment;

public class AnonymousReportActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private TextInputEditText edTime;
    private TextInputEditText edDate;
    private TextInputEditText edStreet;
    private TextInputEditText edNeighborhood;
    private TextInputEditText edNumber;
    private TextInputEditText edCep;
    private TextInputEditText edExtras;
    private TextInputEditText edReferential;
    private TextInputEditText edEstado;
    private TextInputEditText edMonicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_report);
        getSupportActionBar().setTitle(R.string.denuncia_anonima);

        /** ---- Preset. DataPickerDialog --- */
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showCalendar(year, monthOfYear, dayOfMonth);
            }
        };

        /** --- Setup Views --- */
        edStreet = findViewById(R.id.edStreet);
        edNeighborhood = findViewById(R.id.edNeighborhood);
        edNumber = findViewById(R.id.edNumber);
        edCep = findViewById(R.id.edCep);
        edExtras = findViewById(R.id.edExtras);
        edReferential = findViewById(R.id.edReferential);
        edDate = findViewById(R.id.edDate);
        edEstado = findViewById(R.id.edEstado);
        edMonicipio = findViewById(R.id.edMonicipio);
        edMonicipio.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            verifyToSend();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AnonymousReportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edTime = findViewById(R.id.edTime);
        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyToSend();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void updateFieldDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        edDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void showCalendar(int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateFieldDate();
    }

    /**
     * This method wait for
     *
     * @param timePicker
     * @param i
     * @param i1
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        TextInputEditText edDate = findViewById(R.id.edTime);
    }


    /**
     * verify required fields()
     */
    private void verifyToSend() {
        boolean allFilled = true;
        // if (edTime.getText().toString().equals("")) allFilled = false;
        if (edDate.getText().toString().equals("")) allFilled = false;
        if (edStreet.getText().toString().equals("")) allFilled = false;
        if (edNeighborhood.getText().toString().equals("")) allFilled = false;
        if (edNumber.getText().toString().equals("")) allFilled = false;
        if (edMonicipio.getText().toString().equals("")) allFilled = false;
        // if (edCep.getText().toString().equals("")) allFilled = false;
        // if (edExtras.getText().toString().equals("")) allFilled = false;
        // if (edReferential.getText().toString().equals("")) allFilled = false;
        if (allFilled) {
            sendReport();
            Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(SysUtils.KEY_MSG_SEND_ANONY_REPORT, "Obrigado por sua Denúncia!\nSeu enviado foi um suscesso!");
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Preencher campos obrigatórios", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to send this AnonymousReport to FirebaseDatabase
     */
    private void sendReport() {
        List<String> tempDate = Arrays.asList(edDate.getText().toString().split("/"));
        String firebaseKeyData = edDate.getText().toString();
        if (tempDate.get(2) != null && tempDate.get(1) != null && tempDate.get(1) != null) {
            firebaseKeyData = tempDate.get(2) + tempDate.get(1) + tempDate.get(1);
        }

        /**
         * Creating the model AnonymousReport()
         */

        AnonymousReport anonymousReport = new AnonymousReport();
        anonymousReport.setDiaDaOcorrencia(edDate.getText().toString());
        anonymousReport.setHoraDaOcorrencia(edTime.getText().toString());
        anonymousReport.setEnderecoDoFato(edStreet.getText().toString());
        anonymousReport.setEstado(edEstado.getText().toString());
        anonymousReport.setNumero(edNumber.getText().toString());
        anonymousReport.setCep(edCep.getText().toString());
        anonymousReport.setComplemento(edExtras.getText().toString());
        anonymousReport.setPontoDeReferencia(edReferential.getText().toString());
        anonymousReport.setBairro(edNeighborhood.getText().toString());
        anonymousReport.setMunicipio(edMonicipio.getText().toString());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(SysUtils.FB_ANONYMOUS_REPORT).child(firebaseKeyData).push().setValue(anonymousReport);
        cleanFild();
    }


    /**
     * Just UX functions
     */

    private void cleanFild() {
        edDate.setText("");
        edTime.setText("");
        edStreet.setText("");
        edNumber.setText("");
        edCep.setText("");
        edExtras.setText("");
        edReferential.setText("");
        edNeighborhood.setText("");
        edMonicipio.setText("");
    }


}
