package com.example.asaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchingDriveActivity extends AppCompatActivity {
    private ArrayList<DriveModel> DriveList = DriveModel.getInstance().getDriveList();
    private ArrayList citiesList = new ArrayList<>();
    private AlertDialog dialog;

    private FireBaseModel firebaseModel;

    private HeaderHandler headerHandler;
    private FooterHandler footerHandler;

    private Calendar calendar;
    private Button name, date, time, from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_drive);

        firebaseModel = new FireBaseModel(SearchingDriveActivity.this);

        headerHandler = new HeaderHandler(this);

        calendar = Calendar.getInstance();

        date = (Button) findViewById(R.id.btn_select_date);
        time = (Button) findViewById(R.id.btn_select_time);
        from = (Button) findViewById(R.id.btn_select_from);
        to = (Button) findViewById(R.id.btn_select_to);

        if(citiesList.isEmpty()) {
            getCityList();
        }

        setupHeader();
        setupFooter();
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

    public void showFilteredDriveList(View view) {
        DriveList.clear();
        firebaseModel.getRef().child("Drive").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot DriveSnapshot : dataSnapshot.getChildren()) {
                    if(from.getText().toString().equals(DriveSnapshot.child("from").getValue(String.class)) && to.getText().toString().equals(DriveSnapshot.child("to").getValue(String.class))){
                    String driveName = DriveSnapshot.child("name").getValue(String.class);
                    String amount = DriveSnapshot.child("amount").getValue(String.class);
                    String date = DriveSnapshot.child("date").getValue(String.class);
                    String time = DriveSnapshot.child("time").getValue(String.class);
                    String from = DriveSnapshot.child("from").getValue(String.class);
                    String to = DriveSnapshot.child("to").getValue(String.class);



                    DriveList.add(new DriveModel(driveName, date, time, from, to, amount));
                    }

                }
                if(DriveList.isEmpty())
                    firebaseModel.showToast("There are no rides to the destination you requested at the moment");
                else
                    startActivity(new Intent(SearchingDriveActivity.this, DriveListActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDriveListOnClick(View view) {
        Log.d("@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@: ");
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

    public void showCityList(View v) {
        String buttonId = getResources().getResourceEntryName(v.getId());
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchingDriveActivity.this);
        builder.setTitle("בחר מיקום");

        // Create a LinearLayout to hold the ListView and EditText
        LinearLayout layout = new LinearLayout(SearchingDriveActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create an EditText for search functionality
        EditText searchEditText = new EditText(SearchingDriveActivity.this);
        searchEditText.setHint("חיפוש");
        layout.addView(searchEditText);

        // Create a ListView to display the data
        ListView listView = new ListView(SearchingDriveActivity.this);
        listView.setAdapter(new ArrayAdapter<>(SearchingDriveActivity.this, android.R.layout.simple_list_item_1, citiesList));
        layout.addView(listView);

        builder.setView(layout);

        // Set a TextWatcher on the search EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the ListView based on the user's input
                ((ArrayAdapter<String>) listView.getAdapter()).getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        // Set an OnItemClickListener on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the item click
                String selectedLocation = (String) listView.getItemAtPosition(position);
                if(buttonId.equals("btn_select_from"))
                    from.setText(selectedLocation);
                if(buttonId.equals("btn_select_to"))
                    to.setText(selectedLocation);
                dialog.dismiss(); // Close the dialog after item selection

                // Perform any actions you need with the selected location
            }
        });

        // Create the AlertDialog
        dialog = builder.create();

        // Display the AlertDialog
        dialog.show();
    }

    public void getCityList(){
        firebaseModel.getRef().child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(citiesList.isEmpty()) {
                    for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                        String cityName = citySnapshot.child("name").getValue(String.class);
                        citiesList.add(cityName);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error here if the data retrieval is unsuccessful
            }
        });
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