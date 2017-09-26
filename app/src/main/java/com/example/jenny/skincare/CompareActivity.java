package com.example.jenny.skincare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jenny on 26/7/2016.
 */
public class CompareActivity extends FragmentActivity{


    public final static String SHARED_PREFS_FILE = "Records";

    static EditText DateEdit;
    TextView result;
    SharedPreferences prefs;
    ArrayList<Record> arrayOfUsers;
    Button analyze;
    ImageView current,past;
    public static String selecteddate;
    double Y;
    double Ydif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        DateEdit = (EditText) findViewById(R.id.editText1);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v);
                showTruitonDatePickerDialog(v);
            }
        });
        current=(ImageView)findViewById(R.id.currentitem);
        past=(ImageView)findViewById(R.id.pastitem) ;
        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        result=(TextView) findViewById(R.id.editText2);
        analyze=(Button)findViewById(R.id.press);
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    arrayOfUsers = (ArrayList<Record>) ObjectSerializer.deserialize(prefs.getString("re", ObjectSerializer.serialize(new ArrayList<Record>())));
                } catch (IOException e) {
                    e.printStackTrace();
                }catch(ClassCastException e){
                    e.printStackTrace();
                }
                if(arrayOfUsers!=null) {
                    for (Record record : arrayOfUsers) {
                        if (record.getTime().equals(selecteddate)) {
                            Y = record.getY();
                            past.setBackgroundColor(record.getColor());
                        }
                    }
                    Record currentitem=arrayOfUsers.get(arrayOfUsers.size() - 1);
                    current.setBackgroundColor(currentitem.getColor());
                    Ydif=(currentitem.getY()-Y)*100/Y;
                    Ydif=Math.floor(Ydif * 100) / 100;
                    result.setText(Double.toString(Ydif)+"%");
                }

            }
        });

    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateEdit.setText(day + "/0" + (month + 1) + "/" + year);


        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            DateEdit.setText(DateEdit.getText() + " " + hourOfDay + ":" + minute);
            selecteddate=DateEdit.getText().toString();
        }
    }
}