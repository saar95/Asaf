package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeTextView;

    private FireBaseModel fireBaseModel;

    private HeaderHandler headerHandler;
    private FooterHandler footerHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fireBaseModel = new FireBaseModel(this);
        setupWelcomeTextView();
        setupHeader();
        setupFooter();
    }

    public void setupWelcomeTextView() {
        welcomeTextView = findViewById(R.id.text_welcome);
        welcomeTextView.setText("בוקר טוב, " +fireBaseModel.getFirstName());
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

    private void setupFooter() {
        // Find the buttons in the footer layout
        AppCompatButton mySuggestionsButton = findViewById(R.id.btn_my_suggestions);
        AppCompatButton myRidesButton = findViewById(R.id.btn_my_rides);
        AppCompatButton myChatsButton = findViewById(R.id.btn_my_chats);

        // Create an instance of FooterHandler and set it as the OnClickListener for the buttons
        footerHandler = new FooterHandler(this);
        mySuggestionsButton.setOnClickListener(footerHandler);
        myRidesButton.setOnClickListener(footerHandler);
        myChatsButton.setOnClickListener(footerHandler);
    }
}

