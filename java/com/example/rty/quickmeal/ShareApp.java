package com.example.rty.quickmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ShareApp extends AppCompatActivity {

    private Button shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);

        //Set activity title and back button
        getSupportActionBar().setTitle("Share App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --------- Share the App link on click of Share button ---------
        shareButton = (Button)findViewById(R.id.shareapp);
        shareButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                String link = "com.example.rty.MainActivity";

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, link);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.share_App_via)));
            }
        });


    }
}