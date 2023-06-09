package com.example.asaf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.asaf.handler.FooterHandler;
import com.example.asaf.handler.HeaderHandler;
import com.example.asaf.model.FireBaseModel;
import com.example.asaf.utils.DateTimeUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class AddDriveActivity extends AppCompatActivity {
    private FireBaseModel firebaseModel;
    private HeaderHandler headerHandler;
    private FooterHandler footerHandler;
    private EditText amount;
    private Button name, date,  time, from, to;

    private Calendar calendar;
    private ArrayList citiesList = new ArrayList<>();
    private AlertDialog dialog;
    ArrayList<DriveModel> DriveList = DriveModel.getInstance().getDriveList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drive);
        firebaseModel = new FireBaseModel(AddDriveActivity.this);
        headerHandler = new HeaderHandler(this);
        calendar = Calendar.getInstance();
        date = (Button) findViewById(R.id.btn_select_date);
        time = (Button) findViewById(R.id.btn_select_time);
        from = (Button) findViewById(R.id.btn_select_from);
        to = (Button) findViewById(R.id.btn_select_to);
        amount = findViewById(R.id.passenger_count_text);

        if(citiesList.isEmpty()) {
            getCityList();
        }
        setupHeader();
        setupFooter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void driveDetailsOnClick(View view){
        if(!validation(date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount)){

        }
        else{
            FirebaseUser user = firebaseModel.getCurrentUser();
            String userId = user.getUid();
            firebaseModel.saveDrive(firebaseModel.getFullName(),userId,date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString());
            String selectedName = firebaseModel.getFullName();
            String selectedDate = date.getText().toString();
            String selectedTime = time.getText().toString();
            String selectedFrom = from.getText().toString();
            String selectedTo = to.getText().toString();
            String selectedAmount = amount.getText().toString();
            //DriveList.add(new DriveModel(firebaseModel.getFullName(),date.getText().toString(),time.getText().toString(),from.getText().toString(),to.getText().toString(),amount.getText().toString()));
            Intent intent = new Intent(AddDriveActivity.this, DriveListActivity.class);
            intent.putExtra("name",selectedName);
            intent.putExtra("date", selectedDate);
            intent.putExtra("time", selectedTime);
            intent.putExtra("from", selectedFrom);
            intent.putExtra("to", selectedTo);
            intent.putExtra("amount", selectedAmount);
            startActivity(intent);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validation(String date, String  time, String from, String to, EditText amount){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        LocalTime currentTime = LocalTime.now();
        LocalTime timeToCheck = LocalTime.parse(time);

        if(date.equals("DD/MM/YYYY")){
            Toast.makeText(AddDriveActivity.this, "בחר תאריך", Toast.LENGTH_LONG).show();return false;}
        try {
            Date dateToCheck = sdf.parse(date);
            boolean isBeforeToday = dateToCheck.before(today);
            boolean isToday = sdf.format(dateToCheck).equals(sdf.format(today));

            if(isToday){}
            else if (isBeforeToday) {
                Toast.makeText(AddDriveActivity.this, "בחר תאריך עתידי", Toast.LENGTH_LONG).show();return false;}
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (timeToCheck.isBefore(currentTime)) {
            Toast.makeText(AddDriveActivity.this, "בחר שעה עתידית", Toast.LENGTH_LONG).show();return false;}

        if(from.equals(to)){
            Toast.makeText(AddDriveActivity.this, "מיקום המוצא והיעד לא יכולים להיות שווים", Toast.LENGTH_LONG).show();return false;}

        if(from.equals("בחר מיקום")){
            Toast.makeText(AddDriveActivity.this, "Please fill from where is the drive", Toast.LENGTH_LONG).show();return false;}

        if(to.equals("בחר מיקום")){
            Toast.makeText(AddDriveActivity.this, "Please fill to where is the drive", Toast.LENGTH_LONG).show();return false;}

        if(amount.getText().toString().equals("0")){
            Toast.makeText(AddDriveActivity.this, "בחר כמות נוסעים", Toast.LENGTH_LONG).show();return false;}

        return true;
    }

    public void showCityList(View v) {
        String buttonId = getResources().getResourceEntryName(v.getId());
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDriveActivity.this);
        builder.setTitle("בחר מיקום");

        // Create a LinearLayout to hold the ListView and EditText
        LinearLayout layout = new LinearLayout(AddDriveActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create an EditText for search functionality
        EditText searchEditText = new EditText(AddDriveActivity.this);
        searchEditText.setHint("חיפוש");
        layout.addView(searchEditText);

        // Create a ListView to display the data
        ListView listView = new ListView(AddDriveActivity.this);
        listView.setAdapter(new ArrayAdapter<>(AddDriveActivity.this, android.R.layout.simple_list_item_1, citiesList));
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