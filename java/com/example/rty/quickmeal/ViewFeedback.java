package com.example.rty.quickmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewFeedback extends AppCompatActivity {

    /* ------------------ Creating list for recycler view ------------------ */
    private final List<Feedback> feedbacktList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private FeedBackAdapter mAdapter;

    private RecyclerView mRV;

    // ----- Database Ref -------
    private DatabaseReference mContactRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        // --------Set activity title and back button -------------
        getSupportActionBar().setTitle("User Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* ------------------------- Creating list for recycler view ------------------ */
        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        mContactRef = FirebaseDatabase.getInstance().getReference().child("UserFeedBack");

        mAdapter = new FeedBackAdapter(feedbacktList);

        mRV = (RecyclerView) findViewById(R.id.recycler);

        mLinearLayout = new LinearLayoutManager(this);

        mRV.setHasFixedSize(true);

        mRV.setLayoutManager(mLinearLayout);

        mRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRV.setAdapter(mAdapter);

        loadUser();

    }

    private void loadUser() {

        mContactRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();


                    String userName = postSnapshot.child("UserName").getValue().toString();
                    String userFeedback = postSnapshot.child("UserFeedback").getValue().toString();
                    Feedback feedback = new Feedback(userName, userFeedback);

                    feedbacktList.add(feedback);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // -------- Go to Home ----------
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
