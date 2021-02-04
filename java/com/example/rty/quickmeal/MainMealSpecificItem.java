package com.example.rty.quickmeal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rty.quickmeal.Database.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainMealSpecificItem extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mSpecificItemRef;
    private DatabaseReference mOrderRef;

    private ImageView mSpecItemImage;
    private TextView mSpecitemName;
    private TextView mSpecitemPrice;
    private TextView mSpecItemDesc;

    private Button mAddToCart;
    private Button mMoreOrder;
    private Button mShowOrder;

    int quantity=1;
    private TextView mQuantity;

    private TextView mItemQuantity;
    private int itemQuantity;
    private String totalPrice;

    private String itemIdUniqueTest;

    private List<Unique> uniqueId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meal_specific_item);

        displayQuantity1(quantity);
        displayQuantity2(quantity);

        //Set activity title and back button
        getSupportActionBar().setTitle("Meal Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --------------- Initialization -------------
        final String specificItemId = getIntent().getStringExtra("ItemId");

        mSpecitemName = (TextView) findViewById(R.id.spec_item_name);
        mSpecitemPrice = (TextView) findViewById(R.id.spec_item_price);
        mSpecItemDesc = (TextView) findViewById(R.id.item_desc);
        mSpecItemImage= (ImageView)findViewById(R.id.item_image);
        mItemQuantity=(TextView) findViewById(R.id.show_quantity);

        mAddToCart = (Button) findViewById(R.id.add_to_cart);
        mShowOrder = (Button) findViewById(R.id.show_cart);
        mQuantity = (TextView) findViewById(R.id.show_quantity);

        mAuth = FirebaseAuth.getInstance();
        mSpecificItemRef = FirebaseDatabase.getInstance().getReference().child("MainMeal").child(specificItemId);
        mOrderRef = FirebaseDatabase.getInstance().getReference().child("orders");

        // ------------- Fetch data from Firebase database --------------
        mSpecificItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String itemId = dataSnapshot.child("itemId").getValue().toString();
                final String itemName = dataSnapshot.child("itemName").getValue().toString();
                final String itemPrice = dataSnapshot.child("itemPrice").getValue().toString();
                final String itemDesc = dataSnapshot.child("itemDesc").getValue().toString();
                final String itemImage = dataSnapshot.child("itemImage").getValue().toString();

                // Set data values to the existign fields ----------
                mSpecitemName.setText(itemName);
                mSpecitemPrice.setText(itemPrice);
                mSpecItemDesc.setText(itemDesc);
                Picasso.with(getApplicationContext()).load(itemImage).into(mSpecItemImage);


                // -------------------------------- Price & Quantity Conversion ----------------------------

                mAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                        itemQuantity= Integer.parseInt(mItemQuantity.getText().toString());
                        int price = Integer.parseInt(itemPrice);
                        final int singleItemPrice = price * itemQuantity;
                        final String qty = String.valueOf(itemQuantity);
                        totalPrice = String.valueOf(singleItemPrice);

                        uniqueId = new Database(view.getContext()).getUnique();

                        //---------------- Check  if cart is empty -----------------
                        if(uniqueId.isEmpty()){
                            itemIdUniqueTest = itemId;
                        }

                        // --------- Check if data is already added to cart ------------
                        else{
                            for(Unique id:uniqueId) {

                                String compareId = id.getItemId();

                                if(compareId.equals(itemId)) {

                                    itemIdUniqueTest = "-1";
                                    break;
                                }
                                else {

                                    itemIdUniqueTest = itemId;

                                }

                            }
                        }

                        //Toast.makeText(DessertSpecificItem.this, itemIdUniqueTest, Toast.LENGTH_SHORT).show();
                        if(itemIdUniqueTest.equals(itemId)) {

                            new Database(getBaseContext()).addToCart(new Order(
                                    itemId,
                                    itemName,
                                    itemPrice,
                                    itemDesc,
                                    qty,
                                    totalPrice,
                                    itemImage
                            ));

                            Toast.makeText(MainMealSpecificItem.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(MainMealSpecificItem.this, "Item already added to Cart", Toast.LENGTH_SHORT).show();

                        }

                    }

                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        //show cart
        mShowOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cartIntent = new Intent(MainMealSpecificItem.this, Cart.class);
                startActivity(cartIntent);
            }
        });

    }

    // ---------- Increase item quantity ---------
    public void addQuantity(View v) {
        if(quantity<10) {
            quantity = quantity + 1;

        }
        else{
            Toast.makeText(getApplicationContext(),"Maximum quantity for an item is 10",Toast.LENGTH_SHORT ).show();
        }
        displayQuantity1(quantity);
    }

    // -------- show the value of updated quantity after increment ----------
    public void displayQuantity1(int q) {
        TextView quantityView = (TextView) findViewById(R.id.show_quantity);
        quantityView.setText(String.valueOf(quantity));
    }

    // --------- Decreas item quantity --------
    public void removeQuantity(View v) {

        if(quantity>1) {
            quantity = quantity - 1;

        }
        else{
            Toast.makeText(getApplicationContext(),"Minimum quantity for an item is 1",Toast.LENGTH_SHORT ).show();
        }
        displayQuantity2(quantity);
    }

    // -------- show the value of updated quantity after increment ----------
    public void displayQuantity2(int q) {
        TextView quantityView = (TextView) findViewById(R.id.show_quantity);
        quantityView.setText(String.valueOf(quantity));
    }

    //home button link
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