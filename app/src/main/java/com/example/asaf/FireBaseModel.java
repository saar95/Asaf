package com.example.asaf;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseModel {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public FireBaseModel() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void signUp(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

}