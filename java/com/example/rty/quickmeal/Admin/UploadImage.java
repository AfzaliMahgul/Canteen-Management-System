package com.example.mahgul.quickmeal.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.rty.quickmeal.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static java.lang.Math.random;


public class UploadImage extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    private static final int MAX_LENGTH = 20;

    /* ----------------- Database -------------------- */
    private StorageReference mRegUploadStorage;
    private DatabaseReference mRegUploadDatabase;
    private FirebaseUser mCurrentUser;

    private ImageView mRegUploadImage;
    private ProgressDialog mRegUploadProgress;

    private String itemId;

//    private static final int RESULT_LOAD_IMAGE = 1;
//
//    private ImageView mItemImage;
//
//    private FirebaseAuth mAuth;
//    private DatabaseReference mItemRef;
//    private StorageReference mItemStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        itemId = getIntent().getStringExtra("ItemId");

        mRegUploadStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_userId = mCurrentUser.getUid();


        mRegUploadImage = (ImageView) findViewById(R.id.item_image);


        mRegUploadDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItems").child("Dessert").child(itemId);
        mRegUploadDatabase.keepSynced(true);

            /* ----------------------  ------------------------------ */
        mRegUploadDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String image = dataSnapshot.child("itemImage").getValue().toString();

                if(!image.equals("default")) {

                    Picasso.with(UploadImage.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.profile).into(mRegUploadImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(UploadImage.this).load(image).placeholder(R.drawable.profile).into(mRegUploadImage);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mRegUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });






//        String itemId = getIntent().getStringExtra("ItemId");
//
//        mItemStorage = FirebaseStorage.getInstance().getReference();
//        mItemRef = FirebaseDatabase.getInstance().getReference().child("FoodItems").child("Dessert").child(itemId);
//
//
//        mItemImage = (ImageView) findViewById(R.id.item_image);
//
//
//
//        mItemImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
//            }
//        });
    }


//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
//
//            if(data.getClipData() != null) {
//
//                int totalItemSelected = data.getClipData().getItemCount();
//
//                for(int i = 0; i < totalItemSelected; i++) {
//
//                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
//
//                    final String fileName = getFileName(fileUri);
//
//                    StorageReference fileToUpload = mItemStorage.child("ItemImages").child(fileUri.getLastPathSegment());
//
//                    final int finalI = i;
//                    fileToUpload.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                            if(task.isSuccessful()) {
//
//                                String downloadUrl = task.getResult().getDownloadUrl().toString();
//
//
//                                //uploadListAdapter.notifyDataSetChanged();
//
//                                Map uploadMap = new HashMap();
//                                uploadMap.put("itemImage"+finalI, downloadUrl);
//
//                                mItemRef.push().setValue(uploadMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if(task.isSuccessful()) {
//
//                                            Toast.makeText(UploadImage.this, "Uploaded Successfully...", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    });
//
//
//                }
//                //Toast.makeText(MultipleFileUpload.this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();
//            }
//            else if(data.getData() != null) {
//
//                Toast.makeText(UploadImage.this, "Selected Single File", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//}
//    public String getFileName(Uri uri) {
//        String result = null;
//        if (uri.getScheme().equals("content")) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        if (result == null) {
//            result = uri.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut + 1);
//            }
//        }
//        return result;
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {

                mRegUploadProgress = new ProgressDialog(UploadImage.this);
                mRegUploadProgress.setTitle("Uploading Image...");
                mRegUploadProgress.setMessage("Please wait while we upload and process the image");
                mRegUploadProgress.setCanceledOnTouchOutside(false);
                mRegUploadProgress.show();

                try{

                    Uri resultUri = result.getUri();
                    //Toast.makeText(UploadImage.this, resultUri.toString(), Toast.LENGTH_SHORT).show();

                    File thumbFilePath = new File(resultUri.getPath());
                    String currentUserId = mCurrentUser.getUid();

                    Bitmap thumbBitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumbFilePath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    final byte[] thumbByte = baos.toByteArray();


                    StorageReference filePath = mRegUploadStorage.child("profile_images").child(random() + ".jpg");
                    final StorageReference SRThumbFilePath = mRegUploadStorage.child("profile_images").child("thumbs").child(itemId + "jpg");

                    filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()) {

                                final String downloadUrl = task.getResult().getDownloadUrl().toString();

                                UploadTask uploadTask = SRThumbFilePath.putBytes(thumbByte);
                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumbTask) {

                                        String thumbDownloadUrl = thumbTask.getResult().getDownloadUrl().toString();

                                        if(thumbTask.isSuccessful()) {

                                            Map uploadHashMap = new HashMap();
                                            uploadHashMap.put("itemImage", downloadUrl);
                                            uploadHashMap.put("thumbImage", thumbDownloadUrl);

                                            mRegUploadDatabase.updateChildren(uploadHashMap).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {

                                                    if(task.isSuccessful()) {

                                                        Toast.makeText(UploadImage.this, "Successful Upload...", Toast.LENGTH_SHORT).show();
                                                        mRegUploadProgress.dismiss();
                                                    }

                                                }
                                            });
                                        }
                                        else {

                                            Toast.makeText(UploadImage.this, "Error While Uploading Thumbnail....", Toast.LENGTH_SHORT).show();
                                            mRegUploadProgress.dismiss();
                                        }
                                    }
                                });
                            }
                            else {

                                Toast.makeText(UploadImage.this, "Error While Uploading Photo...", Toast.LENGTH_SHORT).show();
                                mRegUploadProgress.dismiss();
                            }

                        }
                    });

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}


