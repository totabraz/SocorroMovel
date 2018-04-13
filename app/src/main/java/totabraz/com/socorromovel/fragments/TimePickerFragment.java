package totabraz.com.socorromovel.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import totabraz.com.socorromovel.R;

/**
 * Created by totabraz on 13/04/18.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextInputEditText edTime =  getActivity().findViewById(R.id.edTime);
        //Set a message for user

        //Display the user changed time on TextView
        String time =  ""+hourOfDay+ ":" + minute;
        edTime.setText(time);
    }
}

