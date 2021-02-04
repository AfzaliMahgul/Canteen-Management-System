package com.example.rty.quickmeal.modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.rty.quickmeal.MainMenu;
import com.example.rty.quickmeal.R;

import java.util.Random;

public class opt extends AppCompatActivity {


    Button send,validate;
    String otp1,message1;
    Bundle bundle1=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt);

        send=(Button) findViewById(R.id.button_send);
        validate=(Button) findViewById(R.id.button_validate);

        //-------- Set activity title and back button -----------
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Random r = new Random( System.currentTimeMillis() );
                int v1= 100000 + r.nextInt(999999);
                String otp=String.valueOf(v1);
                String to="a.kashifi786@gmail.com";
                otp1 = otp;
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, "here is your OTP");
                email.putExtra(Intent.EXTRA_TEXT, otp);
                bundle1.putString("otp1",otp1);
                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));



            }
        });
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Validate_Otp.class);
                i.putExtras(bundle1);
                startActivity(i);
            }
        });

    }
    // -------- Go to home -------------
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

