package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void addDriveOnClick(View view){
       startActivity(new Intent(HomeActivity.this,AddDriveActivity.class));
    }
    public void searchDriveOnClick(View view){
        startActivity(new Intent(HomeActivity.this,SearchingDriveActivity.class));
    }
}