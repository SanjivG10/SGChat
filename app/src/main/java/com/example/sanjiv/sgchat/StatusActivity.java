package com.example.sanjiv.sgchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private Button save_status_button;
    private TextInputLayout your_status;
    private DatabaseReference databaseReference_status;
    private FirebaseUser mUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

    String status = getIntent().getStringExtra("user_status");
    mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.status_layout);
    save_status_button = (Button) findViewById(R.id.save_status_button);
    your_status = (TextInputLayout) findViewById(R.id.your_status);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle("Account Status");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    mUser = FirebaseAuth.getInstance().getCurrentUser();
    String current_user = mUser.getUid();
    databaseReference_status = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);

    your_status.getEditText().setText(status);




    save_status_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressDialog = new ProgressDialog(StatusActivity.this);

            progressDialog.setTitle(" Updating Your Quote ");
            progressDialog.setMessage("This may take some time. Please wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            String status = your_status.getEditText().getText().toString().trim();
            databaseReference_status.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(StatusActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        finish();



                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()," Some Error Occured ",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    });

    }
}
