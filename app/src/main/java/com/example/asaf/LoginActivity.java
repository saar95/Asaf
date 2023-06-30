    package com.example.asaf;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.api.ApiException;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseUser;

    public class LoginActivity extends AppCompatActivity {

        private EditText emailEditText, passwordEditText;
        private FireBaseModel firebaseModel;

        GoogleSignInOptions gsio;
        GoogleSignInClient gsc;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            firebaseModel = new FireBaseModel(LoginActivity.this);

            gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            gsc = GoogleSignIn.getClient(this,gsio);

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
                                HomeActivity();
                            } else {
                                // Login failed
                                Toast.makeText(LoginActivity.this, "Login failed, Please check your data and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

        public void googleLoginOnClick(View view) {
            Intent intent = gsc.getSignInIntent();
            startActivityForResult(intent,100);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==100){
                Task <GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {
                    task.getResult(ApiException.class);
                    HomeActivity();
                } catch (ApiException e) {
                    Toast.makeText(this,"can't connect",Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void HomeActivity(){
            finish();
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }

        public void moveRegisterOnClick(View view) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
