package com.example.asaf;

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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class AddDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private EditText amount;
    private Button name, date,  time, from, to;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drive);
        firebaseModel = new FireBaseModel(AddDriveActivity.this);

        calendar = Calendar.getInstance();

        date = (Button) findViewById(R.id.btn_select_date);
        time = (Button) findViewById(R.id.btn_select_time);
        from = (Button) findViewById(R.id.btn_select_from);
        to = (Button) findViewById(R.id.btn_select_to);

        amount = findViewById(R.id.passenger_count_text);

    }

    public void addDrive(View view){
        FirebaseUser user = firebaseModel.getCurrentUser();
        String userId = user.getUid();
        firebaseModel.saveDrive(userId,date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString());
        startActivity(new Intent(AddDriveActivity.this,HomeActivity.class));
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSelectedDate();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void showTimePickerDialog(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateSelectedTime();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateSelectedDate() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());
        date.setText(selectedDate);
    }

    private void updateSelectedTime() {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        String selectedTime = sdf.format(calendar.getTime());
        time.setText(selectedTime);
    }

    public void plus(View view){
        int newAmount = Integer.valueOf(amount.getText().toString());
        if(newAmount<=3){
            newAmount++;
            amount.setText(Integer.toString(newAmount));
        }
        else{
            Toast.makeText(AddDriveActivity.this, "Please select amount between 1 and 4", Toast.LENGTH_LONG).show();

        }

    }

    public void minus(View view){
        int newAmount = Integer.valueOf(amount.getText().toString());
        if(newAmount>1){
            newAmount--;
            amount.setText(Integer.toString(newAmount));
        }
        else{
            Toast.makeText(AddDriveActivity.this, "Please select amount between 1 and 4", Toast.LENGTH_LONG).show();


        }
    }
}