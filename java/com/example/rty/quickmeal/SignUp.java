package com.example.rty.quickmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.app.ProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConf;
    private Button register;
    private Button back;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mRegisterDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.signup);
        back = (Button)findViewById(R.id.backButton);

        mName = (EditText) findViewById(R.id.uname);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordConf = (EditText) findViewById(R.id.confpassword);
        mProgress = new ProgressDialog(this);

        //Set activity title and back button
        getSupportActionBar().setTitle("Register");



        //-------- Go back to Login Activity ---------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //-------- Register User -------
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mName.getText().toString();
                String userEmail = mEmail.getText().toString();
                String userPassword = mPassword.getText().toString();
                String userPasswordConf = mPasswordConf.getText().toString();


                // ------- Email Validation pattern ------
                String emailValidate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                // ------------ Input validation -----------
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(SignUp.this, "Please enter an Email Id!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userEmail.matches(emailValidate) != true){
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email Id!\ne.g. admin@gmail.com", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(SignUp.this, "Please enter an User Name!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userPassword)) {

                    Toast.makeText(SignUp.this, "Please enter a Passowrd!",Toast.LENGTH_LONG).show();
                    return;

                }
                if (userPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password is too short, please enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPassword.length() > 14) {
                    Toast.makeText(getApplicationContext(), "Password is too long, please enter maximum 14 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!userPassword.equals(userPasswordConf)) {
                    Toast.makeText(getApplicationContext(), "Password does not match!", Toast.LENGTH_SHORT).show();
                    return;
                }


                else{

                    // ------- Display progress bar --------
                    mProgress.setTitle("Registering...");
                    mProgress.setMessage("Please wait a while to register!");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    // ------------- Call RegisterUser function to get registration done --------------
                    RegisterUser(userName, userEmail, userPassword);
                }
            }


        });
    }




    public void RegisterUser(final String userName, final String userEmail, final String userPassword) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mRegisterDatabaseRef = FirebaseDatabase.getInstance().getReference().child("User").child(current_user_id);

                    HashMap<String, String> RegMap = new HashMap<>();
                    RegMap.put("UserName", userName);
                    RegMap.put("UserEmail", userEmail);
                    RegMap.put("UserPassword", userPassword);
                    RegMap.put("DeviceToken", deviceToken);

                    mRegisterDatabaseRef.setValue(RegMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {

                                mProgress.dismiss();
                                Toast.makeText(SignUp.this, "User Registered Successfully!", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            }
                        }
                    });
                }else{
                    mProgress.dismiss();
                    Toast.makeText(SignUp.this,"User Registration failed!"+task.getException().getMessage()+"\nPlease try again...",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
