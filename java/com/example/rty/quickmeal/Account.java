package com.example.rty.quickmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private TextView mUname;
    private TextView mEmail;
    private TextView mPassowrd;
    private FirebaseAuth mAuth;
    private Button back;
    private DatabaseReference mDBReference;

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //-------Set activity title and back button ----------
        getSupportActionBar().setTitle("Account");

        mUname= (TextView)findViewById(R.id.uname);
        mEmail= (TextView)findViewById(R.id.email);
        mPassowrd= (TextView)findViewById(R.id.password);

        mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user= mAuth.getCurrentUser();
        uid= user.getUid();
        mDBReference= FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        if(mAuth.getCurrentUser()== null){

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        // -------- Access the user data from firebase --------
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName, userEmail, userPassword;
                userName= dataSnapshot.child("UserName").getValue().toString();
                userEmail= dataSnapshot.child("UserEmail").getValue().toString();
                userPassword= dataSnapshot.child("UserPassword").getValue().toString();

                mUname.setText(userName);
                mEmail.setText(userEmail);
                mPassowrd.setText(userPassword);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        back = (Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });



    }

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
