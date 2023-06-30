package com.example.asaf;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseModel {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Context context;

    public FireBaseModel(Context context) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        this.context = context.getApplicationContext();
    }

    public void signUp(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void saveUserInfo(String userId, String name, String email, String phone) {
        DatabaseReference usersRef = myRef.child("users").child(userId);

        usersRef.child("name").setValue(name);
        usersRef.child("email").setValue(email);
        usersRef.child("dirvesCounter").setValue(0);
        usersRef.child("rank").setValue(0);
        usersRef.child("phone").setValue(phone)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User info saved successfully
                            showToast("User info saved successfully");
                        } else {
                            // Failed to save user info
                            showToast("Failed to save user info");
                        }
                    }
                });
    }

    public void saveDrive(String userId,String date, String time,String from,String to,String amount){


        DatabaseReference usersRef = myRef.child("Drive").child(userId);

        usersRef.child("date").setValue(date);
        usersRef.child("time").setValue(time);
        usersRef.child("from").setValue(from);
        usersRef.child("to").setValue(to);
        usersRef.child("amount").setValue(amount)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    // User info saved successfully
                    showToast("Drive saved successfully");
                } else {
                    // Failed to save user info
                    showToast("Failed to save drive info");
                }
            }
        });
    }

    public void saveCity(String cityName) {
        DatabaseReference citiesRef = myRef.child("cities");

        // Generate a unique key for the city
        String cityKey = citiesRef.push().getKey();

        // Save the city name under the generated key
        citiesRef.child(cityKey).setValue(cityName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("City saved successfully");
                        } else {
                            showToast("Failed to save city");
                        }
                    }
                });
    }


    public void loadDrive(){

    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
