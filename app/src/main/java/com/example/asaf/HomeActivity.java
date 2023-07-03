package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeTextView;

    FireBaseModel fireBaseModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeTextView = findViewById(R.id.text_welcome);
        fireBaseModel = new FireBaseModel(this);
        welcomeTextView.setText("בוקר טוב ," +fireBaseModel.getFirstName());
    }


    public void addDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, AddDriveActivity.class));
    }

    public void searchDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, SearchingDriveActivity.class));
    }

//    public void getName(){
//        fireBaseModel.getRef().child("users")
//                .child(fireBaseModel.getmAuth().getCurrentUser().getUid())
//                .child("first_name").get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    String name = String.valueOf(task.getResult().getValue());
//                    welcomeEditText.setText("בוקר טוב, "+name);
//                }
//            }
//        });
//    }


}

