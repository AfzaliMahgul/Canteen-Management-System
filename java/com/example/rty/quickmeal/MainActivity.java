package com.example.rty.quickmeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {

    // ------- Variable declaration ---------
    private TextView mRegister;
    private TextView mForgotPass;
    private EditText mEmail;
    private EditText mPassword;
    private Button login;
    private ProgressDialog mProgress;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mRegisterDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ------ Intialization -------
        login = (Button) findViewById(R.id.signin);
        mPassword = (EditText) findViewById(R.id.password);
        mEmail = (EditText) findViewById(R.id.email);
        mProgress = new ProgressDialog(this);
        login = (Button) findViewById(R.id.signin);
        mRegister= (TextView)findViewById(R.id.regester);
        mForgotPass= (TextView)findViewById(R.id.forgotp);

        // -------- Creating Firebase instanse ----------
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Login");

        // -------- if user is already logged in go to App main page -----------
        if (mAuth.getCurrentUser() != null) {
            //Toast.makeText(MainActivity.this, "User is already logged in", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));


        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                // ------- Email Validation pattern ------
                String emailValidate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter an Email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.matches(emailValidate) != true){
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email!\ne.g. admin@gmail.com", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //User login authenticate
                mProgress.setTitle("User Login");
                mProgress.setMessage("Logging in... \nPlease wait for a while!");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                //mProgressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // if there is an error
                                    if (password.length() < 6) {
                                        mPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        mProgress.dismiss();
                                        Toast.makeText(MainActivity.this, "Please enter the correct Password!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mProgress.dismiss();
                                    Toast.makeText(MainActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                                }
                            }
                        });
            }
        });

        // -------- Go to Forgot password activity --------
        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));

            }
        });

        // ------- Go to Registration/Sign Up activity --------
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
}