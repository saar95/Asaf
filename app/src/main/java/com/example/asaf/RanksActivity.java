package com.example.asaf;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.asaf.handler.FooterHandler;
import com.example.asaf.handler.HeaderHandler;

public class RanksActivity extends AppCompatActivity {

    private HeaderHandler headerHandler;
    private FooterHandler footerHandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);
        setupHeader();
        setupFooter();
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
