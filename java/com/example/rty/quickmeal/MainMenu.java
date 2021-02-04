package com.example.rty.quickmeal;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rty.quickmeal.Admin.AddItem;
import com.example.rty.quickmeal.Database.ClearCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private TextView mEmailDisplay;
    private TextView mDrink;
    private TextView mMainMeal;
    private TextView mSnacks;
    private TextView mDessert;

    private ImageView mDrinkImage;
    private ImageView mMainMealImage;
    private ImageView mSnacksImage;
    private ImageView mDessertImage;


    private View mHeaderView;
    private DatabaseReference mDBReference;

    private String uid;
    private String uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mDrink = (TextView) findViewById(R.id.drink);
        mMainMeal = (TextView) findViewById(R.id.meal);
        mSnacks = (TextView) findViewById(R.id.snacks);
        mDessert = (TextView) findViewById(R.id.dessert);

        mDrinkImage = (ImageView) findViewById(R.id.drink_image);
        mMainMealImage = (ImageView) findViewById(R.id.main_meal_image);
        mSnacksImage = (ImageView) findViewById(R.id.snacks_image);
        mDessertImage = (ImageView) findViewById(R.id.dessert_image);



        // ---------- Display Navigation Menu -------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ------------ Display user profile on navigation menu -------------
        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()== null){

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        final FirebaseUser user= mAuth.getCurrentUser();
        uid= user.getUid();
        mDBReference= FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        // ------------ Geting the current user name from database ------------
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uName= dataSnapshot.child("UserName").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        FirebaseUser currentUser= mAuth.getCurrentUser();

        // ------------ Display User Name into Navigation Header ----------
        mHeaderView= navigationView.getHeaderView(0);
        mEmailDisplay= (TextView)mHeaderView.findViewById(R.id.emaildisplay);

        String emailDisp= currentUser.getEmail();
        mEmailDisplay.setText(emailDisp);


        //------------- Go to Drink List Activity -------------
        mDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent drinkIntent = new Intent(MainMenu.this, ItemListDrink.class);
                startActivity(drinkIntent);
            }
        });
        mDrinkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent drinkIntent = new Intent(MainMenu.this, ItemListDrink.class);
                startActivity(drinkIntent);
            }
        });

        //------------- Go to Main Meal List Activity -------------
        mMainMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainMealIntent = new Intent(MainMenu.this, ItemListMainMeal.class);
                startActivity(mainMealIntent);
            }
        });
        mMainMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainMealIntent = new Intent(MainMenu.this, ItemListMainMeal.class);
                startActivity(mainMealIntent);
            }
        });

        //------------- Go to Snacks List Activity -------------
        mSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent snacksIntent = new Intent(MainMenu.this, SnacksList.class);
                startActivity(snacksIntent);
            }
        });
        mSnacksImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent snacksIntent = new Intent(MainMenu.this, SnacksList.class);
                startActivity(snacksIntent);
            }
        });

        //------------- Go to Dessert List Activity -------------
        mDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent dessertIntent = new Intent(MainMenu.this, DessertList.class);
                startActivity(dessertIntent);
            }
        });
        mDessertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent dessertIntent = new Intent(MainMenu.this, DessertList.class);
                startActivity(dessertIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // ******** LOGOUT USER*********
        if(item.getItemId() == R.id.action_logout){

            // ---------- Clear Cart data when user logs out ------------
            ClearCart db;
            db= new ClearCart(this);
            db.clearCart();

            // ----------Logout the user ------------
            mAuth.signOut();
            goToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {

        Intent mainIntent = new Intent(MainMenu.this, MainActivity.class);
        startActivity(mainIntent);
    }

    // ----------- Display Navigation Menu Items ---------------
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            finish();
            startActivity(new Intent(getApplicationContext(), Account.class));

        } else if (id == R.id.nav_orderhistory) {

            Intent intent = new Intent(MainMenu.this, OrderHistory.class);
            startActivity(intent);


        } else if (id == R.id.nav_sharefeedback) {
            Intent intent = new Intent(MainMenu.this, ShareFeedback.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_viewfeedback) {
            Intent intent = new Intent(MainMenu.this, ViewFeedback.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_terms) {
            Intent intent = new Intent(MainMenu.this, TermsAndCondition.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainMenu.this, ShareApp.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
