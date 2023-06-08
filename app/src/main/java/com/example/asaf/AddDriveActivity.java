package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private Button plusButton,minusButton;
    private EditText amount;
    private Button name, date,  time, from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drive);
        firebaseModel = new FireBaseModel(AddDriveActivity.this);


        plusButton = (Button) findViewById(R.id.plus_button);
        minusButton = (Button) findViewById(R.id.minus_button);
        amount = findViewById(R.id.passenger_count_text);

    }

    public void addDrive(View view){
        date = (Button)findViewById(R.id.btn_select_date);
        time = (Button)findViewById(R.id.btn_select_time);
        from = (Button)findViewById(R.id.btn_select_from);
        to = (Button)findViewById(R.id.btn_select_to);
        amount = findViewById(R.id.passenger_count_text);
        firebaseModel.saveDrive("Saar",date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString());

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