package com.example.rty.quickmeal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rty.quickmeal.Database.Database;
import com.example.rty.quickmeal.modules.DoPayment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.AlertDialog;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class Cart extends AppCompatActivity {

    public ImageView item_delete;

    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    private LinearLayoutManager mLinearLayout;
    private DatabaseReference mRequest;

    private TextView mTotalPrice;
    private Button mPlaceOrder;

    private List<Order> cart = new ArrayList<>();
    private CartAdapter adapter;

    private String itemId;
    private String itemName;
    private String itemPrice;
    private String itemImage;
    private String itemDesc;
    private Calendar calendar;


    private ArrayList<String> finalOrder = new ArrayList<>();
    private DatabaseReference mPODatabaseRef;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Set activity title and back button
        getSupportActionBar().setTitle("Your Cart");

        mLinearLayout = new LinearLayoutManager(this);


        mRequest = FirebaseDatabase.getInstance().getReference("Requests");

        mRecyclerView = (RecyclerView) findViewById(R.id.list_cart);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mTotalPrice = (TextView) findViewById(R.id.total_amount);

       mPlaceOrder = (Button)findViewById(R.id.place_order);

        mRecyclerView.setAdapter(adapter);

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        mRecyclerView.setAdapter(adapter);



        int total  = 0;
        for(Order order:cart)
            total += (Integer.parseInt(order.getTotalPrice()));


        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        //mTotalPrice.setText(format.format(total));
        mTotalPrice.setText("₹ "+total);


        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        mPODatabaseRef = FirebaseDatabase.getInstance().getReference().child("UserOderHistory").child(currentUserId);

        calendar= Calendar.getInstance();
        final String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        mPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(Order order:cart) {

                    finalOrder.add(order.getItemId());
                    finalOrder.add(order.getItemName());
                    finalOrder.add(order.getItemPrice());
                    finalOrder.add(order.getItemDesc());
                    finalOrder.add(order.getItemQty());
                    finalOrder.add(order.getTotalPrice());
                    finalOrder.add(order.getItemImage());
                    //finalOrder.add(currentDate);

                   // mPODatabaseRef.child("Id : " +random()).setValue(order);
                    mPODatabaseRef.child("Date : " + currentDate +random()).setValue(order);
                    //Toast.makeText(Cart.this, "Item Placed Successfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),DoPayment.class);
                    startActivity(i);

                }

                new Database(view.getContext()).clearCart();
            }
        });

    }


    private static String random() {

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int MAX_LENGTH= 20;
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;

        tempChar = (char) (generator.nextInt(96) + 32);

        randomStringBuilder.append(tempChar);

        return randomStringBuilder.toString();
    }


    // home button
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addtocart, menu);
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
