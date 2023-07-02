package com.example.asaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private EditText welcomeEditText;

    FireBaseModel fireBaseModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeEditText = findViewById(R.id.text_welcome);
        fireBaseModel = new FireBaseModel(this);
        getName();
    }


    public void addDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, AddDriveActivity.class));
    }

    public void searchDriveOnClick(View view) {
        startActivity(new Intent(HomeActivity.this, SearchingDriveActivity.class));
    }

    public String getName(){
        fireBaseModel.getRef().child("users")
                .child(fireBaseModel.getmAuth().getCurrentUser().getUid())
                .child("first_name").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String name = String.valueOf(task.getResult().getValue());
                    welcomeEditText.setText("בוקר טוב, "+name);
                }
            }
        });
        return "first_name";
    }

}

