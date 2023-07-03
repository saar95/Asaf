package com.example.asaf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchingDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private Calendar calendar;
    private Button name, date,  time;
    private TextInputLayout inputFrom,inputTo;
    private AutoCompleteTextView from,to;
    private ArrayList<String> citiesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_drive);

        firebaseModel = new FireBaseModel(SearchingDriveActivity.this);

        calendar = Calendar.getInstance();

        date = (Button) findViewById(R.id.btn_select_date);
        time = (Button) findViewById(R.id.btn_select_time);
        inputFrom = findViewById(R.id.input_from);
        from =findViewById(R.id.search_city);

        citiesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(SearchingDriveActivity.this, android.R.layout.simple_spinner_item, citiesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        from.setAdapter(adapter);
        from.setThreshold(1);
        showCitiesList();
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



    public void showCitiesList(){
        firebaseModel.getRef().child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(citiesList.isEmpty()) {
                    for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                        String cityName = citySnapshot.child("name").getValue(String.class);
                        citiesList.add(cityName);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error here if the data retrieval is unsuccessful
            }
        });
    }

}