package com.example.sanjiv.sgchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mUser;
    private CircleImageView user_image;
    private TextView mName;
    private TextView status;
    private Button change_photo;
    private Button change_status;
    private static final int GALLERY_REQUEST_CODE=1;
    private ProgressDialog mProgressDialog;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    mUser = FirebaseAuth.getInstance().getCurrentUser();
    String user_id = mUser.getUid();
    mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
    mStorageRef = FirebaseStorage.getInstance().getReference();

    user_image = (CircleImageView) findViewById(R.id.default_avatar);
    mName = (TextView) findViewById(R.id.display_username);
    status = (TextView) findViewById(R.id.fav_quote);
    change_photo = (Button) findViewById(R.id.change_photo);
    change_status = (Button) findViewById(R.id.change_name);

    change_status.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String user_status = status.getText().toString();
            Intent intent = new Intent(SettingsActivity.this,StatusActivity.class);
            intent.putExtra("user_status",user_status);
            startActivity(intent);
        }
    });

    change_photo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



            Intent imagePicker = new Intent();
            imagePicker.setType("image/*");
            imagePicker.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(imagePicker,"Choose Your Photo"),GALLERY_REQUEST_CODE);


        }
    });







    mUserDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String name= dataSnapshot.child("name").getValue().toString();
            String fav_quote= dataSnapshot.child("status").getValue().toString();
            String image= dataSnapshot.child("image").getValue().toString();
            String thumbnail= dataSnapshot.child("thumbnail").getValue().toString();

            mName.setText(name);
            status.setText(fav_quote);
            Picasso.with(SettingsActivity.this).load(image).into(user_image);



        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri )
                    .setAspectRatio(1,1)
                    .start(this);


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(SettingsActivity.this);
                mProgressDialog.setTitle(" Uploading Image ");
                mProgressDialog.setMessage(" Please wait, while we process your image ");
                mProgressDialog.show();
                mProgressDialog.setCanceledOnTouchOutside(false);

                Uri resultUri = result.getUri();
                String user_name = mUser.getUid();
                StorageReference filepath = mStorageRef.child("profile_images").child(user_name+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            String download_url = task.getResult().getDownloadUrl().toString();
                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        mProgressDialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(SettingsActivity.this," Some Error Occured ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this," Error! Check ",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }



        }


    }

}
