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
        private FireBaseModel firebaseModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            firebaseModel = new FireBaseModel(LoginActivity.this);

            emailEditText = findViewById(R.id.input_mail);
            passwordEditText = findViewById(R.id.input_password);

        }


                public void loginOnClick(View view) {
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
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                // You can navigate to another activity here
                            } else {
                                // Login failed
                                Toast.makeText(LoginActivity.this, "Login failed, Please check your data and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


        public void moveRegisterOnClick(View view) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
