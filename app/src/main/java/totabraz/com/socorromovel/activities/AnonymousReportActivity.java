package totabraz.com.socorromovel.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.utils.TimePickerFragment;

public class AnonymousReportActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Calendar myCalendar = Calendar.getInstance();
    private TextInputEditText edDate;
    private TextInputEditText edTime;

    private DatePickerDialog.OnDateSetListener date;

    private void updateFieldTime() {

    }

    private void updateFieldDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        edDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_report);

        /** ---- Preset. DataPickerDialog --- */
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showCalendar(year, monthOfYear, dayOfMonth);
            }
        };

        /** --- Setup Views --- */
        edDate = findViewById(R.id.edDate);
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
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showCalendar(int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateFieldDate();
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        TextInputEditText edDate = findViewById(R.id.edTime);
    }
}
