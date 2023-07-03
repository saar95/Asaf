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

import java.util.ArrayList;
import java.util.Locale;

public class AddDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private EditText amount;
    private Button name, date,  time, from, to;

    private Calendar calendar;
    ArrayList<DriveModel> DriveList = DriveModel.getInstance().getDriveList();

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
        if(!validation(date,time,from,to,amount)){

        }
        else{
            FirebaseUser user = firebaseModel.getCurrentUser();
            String userId = user.getUid();
            firebaseModel.saveDrive(firebaseModel.getFullName(),userId,date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString());
            //DriveList.add(new DriveModel(firebaseModel.getFullName(),date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString()));
            startActivity(new Intent(AddDriveActivity.this,HomeActivity.class));
        }

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
    public boolean validation(Button date,Button  time,Button from,Button to,EditText amount){
        if(date.getText().toString().equals("DD/MM/YYYY")){
            Toast.makeText(AddDriveActivity.this, "Please fill the date", Toast.LENGTH_LONG).show();return false;}
        if(from.getText().toString().isEmpty()){
            Toast.makeText(AddDriveActivity.this, "Please fill from where is the drive", Toast.LENGTH_LONG).show();return false;}
//        if(to.getText().toString().equals("בחר מיקום")){
//            Toast.makeText(AddDriveActivity.this, "Please fill to where is the drive", Toast.LENGTH_LONG).show();return false;}
//        if(amount.getText().toString().equals("בחר מיקום")){
//            Toast.makeText(AddDriveActivity.this, "Please fill the amount", Toast.LENGTH_LONG).show();return false;}
        return true;
    }
}