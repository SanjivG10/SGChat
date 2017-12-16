package com.example.sanjiv.sgchat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private TabLayout tab_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SGChat");

        viewPager = (ViewPager) findViewById(R.id.tab_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);
        tab_layout = findViewById(R.id.main_nav_bar);

        //connect the tabs and pageViewer
        tab_layout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if user is not signed in, tell them to go to sign in page
        if (currentUser==null)
        {
            comeToSignInPlace();
        }
    }

    private void comeToSignInPlace() {
        Intent intent = new Intent(MainActivity.this,InitialActivity.class);
        startActivity(intent);
        finish();
        //finish so that when user presses back, they won't come back to MainActivity

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         switch (item.getItemId())
         {
             case R.id.logout_button:
                 mAuth.signOut();
                 comeToSignInPlace();
                 break;
             case R.id.account_settings:
                 Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                 startActivity(intent);
                 break;
             case R.id.all_users:
                 break;
         }

         return true;
    }


}
