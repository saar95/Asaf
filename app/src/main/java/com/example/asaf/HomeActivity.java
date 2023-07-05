package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeTextView;

    FireBaseModel fireBaseModel;

    HeaderHandler headerHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fireBaseModel = new FireBaseModel(this);
        setupWelcomeTextView();
        setupHeader();
    }

    public void setupWelcomeTextView() {
        welcomeTextView = findViewById(R.id.text_welcome);
        welcomeTextView.setText("בוקר טוב ," +fireBaseModel.getFirstName());
    }

    public void addDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, AddDriveActivity.class));
    }

    public void searchDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, SearchingDriveActivity.class));
    }

    private void setupHeader() {
        headerHandler = new HeaderHandler(this);

        ImageButton menuButton = findViewById(R.id.btn_open_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerHandler.openSideMenu();
            }
        });
    }
}

