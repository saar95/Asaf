package com.example.asaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SearchingDriveActivity extends AppCompatActivity {
    ArrayList<DriveModel> DriveList = DriveModel.getInstance().getDriveList();

    private FireBaseModel firebaseModel;
    private Calendar calendar;
    private Button name, date, time, from, to;

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

    public void showDriveList(View view) {
        DriveList.clear();
        firebaseModel.getRef().child("Drive").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot DriveSnapshot : dataSnapshot.getChildren()) {
                    String driveName = DriveSnapshot.child("name").getValue(String.class);
                    String amount = DriveSnapshot.child("amount").getValue(String.class);
                    String date = DriveSnapshot.child("date").getValue(String.class);
                    String time = DriveSnapshot.child("time").getValue(String.class);
                    String from = DriveSnapshot.child("from").getValue(String.class);
                    String to = DriveSnapshot.child("to").getValue(String.class);

                    DriveList.add(new DriveModel(driveName, date, time, from, to, amount));
                }

                startActivity(new Intent(SearchingDriveActivity.this, DriveListActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}





