    package com.example.asaf;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseUser;

    public class LoginActivity extends AppCompatActivity {

        private EditText emailEditText, passwordEditText;
        private Button loginButton;
        private FireBaseModel firebaseModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            firebaseModel = new FireBaseModel(LoginActivity.this);

            emailEditText = findViewById(R.id.input_mail);
            passwordEditText = findViewById(R.id.input_password);
            loginButton = findViewById(R.id.btn_login);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    // Perform field validations
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Call the signIn method in the FirebaseModel
                    firebaseModel.signIn(email, password, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login success
                                FirebaseUser user = firebaseModel.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // You can navigate to another activity here
                            } else {
                                // Login failed
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        public void registerOnClick(View view) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
