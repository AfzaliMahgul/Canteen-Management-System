package com.example.rty.quickmeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Random;

public class ShareFeedback extends AppCompatActivity {

    private EditText mFeedback;private  EditText mName;
    private Button  mSubmit;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private static final int MAX_LENGTH = 20;
    private DatabaseReference mDBReference;
    private String uid;
    private String uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_feedback);

        //Set activity title and back button
        getSupportActionBar().setTitle("Share Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // -------- Initialization ---------
        mFeedback=(EditText)findViewById(R.id.feedback);
        mSubmit=(Button)findViewById(R.id.submitfeedback);
        mProgress = new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user= mAuth.getCurrentUser();
        uid= user.getUid();
        mDBReference= FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        // ------------ Access current user name from database ------------
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName, userEmail, userPassword;
                userName= dataSnapshot.child("UserName").getValue().toString();
                uName=userName;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userfeedback = mFeedback.getText().toString();

                if (TextUtils.isEmpty(userfeedback)){
                    Toast.makeText(ShareFeedback.this, "Please enter feekback!",Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                mRef = FirebaseDatabase.getInstance().getReference().child("UserFeedBack").child(random());;

                mProgress.setTitle("Saving Feedback");
                mProgress.setMessage("Please wait for sometime");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                // ------------ Save the user Feedback to the database ------------
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap<String, String> addMap = new HashMap<>();
                        addMap.put("UserName",uName);
                        addMap.put("UserFeedback", userfeedback);

                        mRef.setValue(addMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()) {

                                    mProgress.dismiss();
                                    Toast.makeText(ShareFeedback.this, "com.example.rty.quickmeal.MainActivity.Feedback saved Successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ShareFeedback.this, "Can not Save feedback\nPlease try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    // ----------- Randon Id generator class ---------
    private static String random() {

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        tempChar = (char) (generator.nextInt(96) + 32);
        randomStringBuilder.append(tempChar);

        return randomStringBuilder.toString();

    }

    // --------- Go to home ----------
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homebutton, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
