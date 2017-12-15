package com.example.sanjiv.sgchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Account_Creation extends AppCompatActivity {

    private TextInputLayout username_layout;
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private Button create_account;
    private FirebaseAuth mAuth;
    private Toolbar create_account_toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__creation);

        mAuth = FirebaseAuth.getInstance();
        username_layout = (TextInputLayout) findViewById(R.id.username);
        email_layout = (TextInputLayout) findViewById(R.id.login_email);
        password_layout = (TextInputLayout) findViewById(R.id.login_password);
        create_account = (Button) findViewById(R.id.create_account);
        create_account_toolbar = (Toolbar)  findViewById(R.id.account_creation_toolbar);
        setSupportActionBar(create_account_toolbar);
        getSupportActionBar().setTitle("Create an Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_layout.getEditText().getText().toString().trim();
                String email = email_layout.getEditText().getText().toString().trim();
                String password = password_layout.getEditText().getText().toString().trim();

                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(password))
                {
                    progressDialog.setTitle(" Creating Your Account ");
                    progressDialog.setMessage(" Creating Your " +
                            "Account. Please wait for a while ");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    registerUser(username, email, password);
                }
                else {
                    Toast.makeText(getApplicationContext()," Some Field/s are empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String username, String email, String password) {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(Account_Creation.this,MainActivity.class);
                        //if users presses back , we dont go back to start Activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                       // Log.e("Error Here","Some Error Occured");
                        progressDialog.hide();
                        Toast.makeText(Account_Creation.this,"Error in Creation",Toast.LENGTH_LONG).show();

                    }
                }
            });
    }
}
