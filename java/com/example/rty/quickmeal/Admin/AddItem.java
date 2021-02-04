package com.example.mahgul.quickmeal.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rty.quickmeal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    private EditText mImageId;
    private EditText mImageName;
    private EditText mImagePrice;
    private EditText mImageDesc;

    private Button mAddImageBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        mImageId = (EditText) findViewById(R.id.image_id);
        mImageName = (EditText) findViewById(R.id.image_name);
        mImagePrice = (EditText) findViewById(R.id.image_price);
        mImageDesc = (EditText) findViewById(R.id.image_desc);

        mAddImageBtn = (Button) findViewById(R.id.add_image);


        mAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String itemId = mImageId.getText().toString();
                final String itemName = mImageName.getText().toString();
                final String itemPrice = mImagePrice.getText().toString();
                final String itemDesc = mImageDesc.getText().toString();


                mAuth = FirebaseAuth.getInstance();
                mItemRef = FirebaseDatabase.getInstance().getReference().child("FoodItems").child("Dessert").child(itemId);

                mItemRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map itemMap = new HashMap();
                        itemMap.put("ItemId", itemId);
                        itemMap.put("ItemName", itemName);
                        itemMap.put("ItemPrice", itemPrice);
                        itemMap.put("ItemDesc", itemDesc);
                        itemMap.put("itemImage", "default");


                        mItemRef.setValue(itemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                finish();
                                mItemRef.onDisconnect();
                                Toast.makeText(AddItem.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();

                                Intent imageIntent = new Intent(AddItem.this, UploadImage.class);
                                imageIntent.putExtra("ItemId", itemId);
                                startActivity(imageIntent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}