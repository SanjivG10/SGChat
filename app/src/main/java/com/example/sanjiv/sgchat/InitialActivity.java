package com.example.sanjiv.sgchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InitialActivity extends AppCompatActivity {
    private Button register_button;
    private Button signin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        register_button = (Button) findViewById(R.id.create_account);
        signin_button = (Button) findViewById(R.id.sign_in_button);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(InitialActivity.this,Account_Creation.class);
                startActivity(intent);

            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(InitialActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }


}
