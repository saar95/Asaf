package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Locale;

public class SearchingDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private Calendar calendar;
    private Button name, date,  time, from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_drive);

        firebaseModel = new FireBaseModel(SearchingDriveActivity.this);

        calendar = Calendar.getInstance();

        date = (Button) findViewById(R.id.btn_select_date);
        time = (Button) findViewById(R.id.btn_select_time);
        from = (Button) findViewById(R.id.btn_select_from);
        to = (Button) findViewById(R.id.btn_select_to);
    }

    public void showDatePickerDialog(View view) {
        DateTimeUtils.showDatePickerDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateTimeUtils.updateSelectedDate(date, calendar);
            }
        });
    }

    public void showTimePickerDialog(View view) {
        DateTimeUtils.showTimePickerDialog(this, calendar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                DateTimeUtils.updateSelectedTime(time, calendar);
            }
        });
    }
}