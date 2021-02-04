package com.example.rty.quickmeal.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rty.quickmeal.R;

public class Validate_Otp extends AppCompatActivity {


    EditText check_otp;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate__otp);
        check_otp=(EditText) findViewById(R.id.check_otp);

        //-------- Set activity title and back button -----------
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        check=(Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getExtras();
                String otp1=bundle.getString("otp1");

                if(check_otp.getText().toString().equals(otp1)){
                    Toast.makeText(getApplicationContext(), "Payment has been validated",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "your otp is wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

