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

public class LoginActivity extends AppCompatActivity {
    private Toolbar login_toolbar;
    private Button login_button;
    private TextInputLayout login_email;
    private TextInputLayout login_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        login_toolbar = (Toolbar) findViewById(R.id.account_creation_toolbar);
        login_button = (Button) findViewById(R.id.login_button);
        login_email = (TextInputLayout) findViewById(R.id.login_email);
        login_password = (TextInputLayout) findViewById(R.id.login_password);

        login_toolbar = (Toolbar) findViewById(R.id.account_login_toolbar);
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setTitle("Login to Your Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getEditText().getText().toString().trim();
                String password = login_password.getEditText().getText().toString();


                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    progressDialog.setTitle(" Logging In ");
                    progressDialog.setMessage(" Please wait while we log you in ");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    loginUser(email,password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext()," Error Signing In ",Toast.LENGTH_SHORT);

                }
            }
        });


    }
}
