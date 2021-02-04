package com.example.rty.quickmeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    private EditText mEmail;
    private Button resetPassword;
    private Button cancel;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmail= (EditText)findViewById(R.id.emailreset);

        mProgress = new ProgressDialog(this);

        // -------Set activity title and back button ---------
        getSupportActionBar().setTitle("Reset Passowrd");

        // -------Creating Firebase instanse ---------
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user= mAuth.getCurrentUser();

        // ------ Reset the password ---------
        resetPassword=(Button)findViewById(R.id.resetpassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = mEmail.getText().toString();

                // ------- Email Validation pattern ------
                String emailValidate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                // ------------ Input validation -----------
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(getApplicationContext(), "Please enter an Email Id!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userEmail.matches(emailValidate) != true){
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email Id!\ne.g. admin@gmail.com", Toast.LENGTH_SHORT).show();
                    return;
                }


                else{
                    mProgress.setTitle("Reset Password");
                    mProgress.setMessage("Sending the user Password link ...\nPease wait for a while! ");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mProgress.dismiss();
                                        Toast.makeText(ResetPassword.this, "Check your Email Id to reset your Password!", Toast.LENGTH_LONG).show();

                                        mAuth.signOut();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Toast.makeText(ResetPassword.this, "Failled to send reset Password email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }
            }
        });

        // ---------- Back to Login Activity ----------
        cancel= (Button)findViewById(R.id.cancelreset);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
