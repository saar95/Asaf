package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asaf.Model.FireBaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, cPasswordEditText, phoneEditText;
    private FireBaseModel firebaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseModel = new FireBaseModel(RegisterActivity.this);

        firstNameEditText = findViewById(R.id.input_first_name);
        lastNameEditText = findViewById(R.id.input_last_name);
        emailEditText = findViewById(R.id.input_mail);
        phoneEditText = findViewById(R.id.input_phone);
        passwordEditText = findViewById(R.id.input_password);
        cPasswordEditText = findViewById(R.id.input_cpassword);

    }

    public void signUpOnClick(View v) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String cPassword = cPasswordEditText.getText().toString();
        String fullName = firstName+" "+lastName;

        if(!validation(firstName,lastName,email,phone,password,cPassword))
            return;

        // Call the signUp method in the FirebaseModel
        firebaseModel.signUp(email, password,fullName  ,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Registration success
                    FirebaseUser user = firebaseModel.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Log.d("RegistrationActivity", "Registration successful for user: " + user.getEmail());
                    // Push user info to the Realtime Database
                    String userId = user.getUid();
                    firebaseModel.saveUserInfo(userId, firstName, lastName, email, phone);
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

                } else {
                    // Registration failed
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    Log.e("RegistrationActivity", "Registration failed: " + task.getException().getMessage());
                }
            }
        });
    }
    public void moveLoginOnClick(View view){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    private boolean validation(String firstName, String lastName, String email, String phone, String password, String cPassword) {
        if (firstName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "First mame is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (lastName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Last name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Phone is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Perform field validations
        if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(RegisterActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidCPassword(password,cPassword)) {
            Toast.makeText(RegisterActivity.this, "הסיסמאות אינן דומות", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidCPassword(String password, String cPassword) {
        return password.equals(cPassword);
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 6 characters long and contain letters and numbers
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
        return password.matches(passwordPattern);
    }
}
