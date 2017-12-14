package com.example.sanjiv.sgchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Account_Creation extends AppCompatActivity {

    private TextInputLayout username_layout;
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private Button create_account;
    private FirebaseAuth mAuth;
    private Toolbar create_account_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__creation);
        mAuth = FirebaseAuth.getInstance();
        username_layout = (TextInputLayout) findViewById(R.id.username);
        email_layout = (TextInputLayout) findViewById(R.id.email);
        password_layout = (TextInputLayout) findViewById(R.id.password);
        create_account = (Button) findViewById(R.id.create_account);
        create_account_toolbar = (Toolbar)  findViewById(R.id.account_creation_toolbar);
        setSupportActionBar(create_account_toolbar);
        getSupportActionBar().setTitle("Create an Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_layout.getEditText().getText().toString().trim();
                String email = email_layout.getEditText().getText().toString().trim();
                String password = password_layout.getEditText().getText().toString().trim();

                registerUser(username,email,password);

            }
        });
    }

    private void registerUser(String username, String email, String password) {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent = new Intent(Account_Creation.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                       // Log.e("Error Here","Some Error Occured");
                        Toast.makeText(Account_Creation.this,"Error in Creation",Toast.LENGTH_LONG).show();

                    }
                }
            });
    }
}
