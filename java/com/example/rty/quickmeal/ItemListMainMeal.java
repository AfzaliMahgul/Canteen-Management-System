package com.example.rty.quickmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemListMainMeal extends AppCompatActivity {


    private RecyclerView mRV;
    private final List<Item> mainMealList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private ItemMMAdapter mAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_main_meal);

        //Set activity title and back button
        getSupportActionBar().setTitle("Main Meal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ----------- Initialization -----------
        mAuth = FirebaseAuth.getInstance();
        mItemRef = FirebaseDatabase.getInstance().getReference().child("MainMeal");

        mAdapter = new ItemMMAdapter(mainMealList);

        mRV = (RecyclerView) findViewById(R.id.main_meal_rv);

        mLinearLayout = new LinearLayoutManager(this);

        mRV.setHasFixedSize(true);
        mRV.setLayoutManager(mLinearLayout);

        mRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRV.setAdapter(mAdapter);

        loadItem();

    }

    //------------ Fetch data from Firebase -------------
    private void loadItem() {

        mItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String itemId = postSnapshot.child("itemId").getValue().toString();
                    String itemName = postSnapshot.child("itemName").getValue().toString();
                    String itemPrice= postSnapshot.child("itemPrice").getValue().toString();
                    String itemImage = postSnapshot.child("itemImage").getValue().toString();


                    Item item = new Item(itemId, itemName, itemImage, itemPrice);

                    mainMealList.add(item);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.homebutton, menu);

        // --------- Search item based on their names-----------
        MenuItem searchItem = menu.findItem(R.id.Action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;

                }
                @Override
                public boolean onQueryTextChange(String newtext) {
                    newtext=newtext.toLowerCase();

                    ArrayList<Item> newlist=new ArrayList<>();
                    for(Item blog :mainMealList)
                    {
                        String Name=blog.getItemName().toLowerCase();

                        if(Name.contains(newtext))
                        {
                            newlist.add(blog);
                        }

                    }
                    mAdapter.setFilter(newlist);

                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
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
