package com.example.rty.quickmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {

    // --------- Declaration ---------
    private RecyclerView mRV;
    private final List<History> orderHistory = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private OrderHistoryAdapter mAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference mItemRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        //Set activity title and back button
        getSupportActionBar().setTitle("Order History");

        // ------ Intialization --------
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user= mAuth.getCurrentUser();
        uid= user.getUid();
        mItemRef = FirebaseDatabase.getInstance().getReference().child("UserOderHistory").child(uid);

        mAdapter = new OrderHistoryAdapter(orderHistory);

        mRV = (RecyclerView) findViewById(R.id.orderHistory_rv);

        mLinearLayout = new LinearLayoutManager(this);

        mRV.setHasFixedSize(true);
        mRV.setLayoutManager(mLinearLayout);

        mRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRV.setAdapter(mAdapter);

        loadItem();

    }

    // ---------- Load item from data base ---------
    private void loadItem() {

        mItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String itemName = postSnapshot.child("itemName").getValue().toString();
                    String itemPrice= postSnapshot.child("itemPrice").getValue().toString();
                    String itemImage = postSnapshot.child("itemImage").getValue().toString();
                    String itemQty= postSnapshot.child("itemQty").getValue().toString();
                    String totalPrice=postSnapshot.child("totalPrice").getValue().toString();

                    History history = new History(itemName, itemImage, itemPrice, itemQty, totalPrice);

                    orderHistory.add(history);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
