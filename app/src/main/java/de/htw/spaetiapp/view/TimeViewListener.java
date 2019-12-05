package de.htw.spaetiapp.view;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeViewListener implements View.OnClickListener {

    private TextView view;

    public TimeViewListener(TextView view){
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(v.getContext(),2, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                view.setText( selectedHour + ":" + selectedMinute);
            }
        }, 0, 0, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
