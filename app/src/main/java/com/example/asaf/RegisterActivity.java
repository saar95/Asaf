package com.example.asaf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, cPasswordEditText, phoneEditText;
    private Button registerButton;
    private FireBaseModel firebaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseModel = new FireBaseModel();

        nameEditText = findViewById(R.id.input_name);
        emailEditText = findViewById(R.id.input_mail);
        phoneEditText = findViewById(R.id.input_phone);
        passwordEditText = findViewById(R.id.input_password);
        cPasswordEditText = findViewById(R.id.input_cpassword);
        registerButton = findViewById(R.id.btn_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String cPassword = cPasswordEditText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Phone is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform field validations
                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidCPassword(password,cPassword)) {
                    Toast.makeText(RegisterActivity.this, "הסיסמאות אינן דומות", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the signUp method in the FirebaseModel
                firebaseModel.signUp(email, password, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            FirebaseUser user = firebaseModel.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Log.d("RegistrationActivity", "Registration successful for user: " + user.getEmail());
                        } else {
                            // Registration failed
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            Log.e("RegistrationActivity", "Registration failed: " + task.getException().getMessage());
                        }
                    }
                });
            }
        });
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
