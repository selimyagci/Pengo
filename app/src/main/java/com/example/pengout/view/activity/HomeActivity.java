package com.example.pengout.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengout.R;
import com.example.pengout.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {


    private Toolbar mToolbar;
//    private ViewPager myViewPager;
//    private TabLayout myTabLayout;
//    private TabsAccessorAdapter myTabsAccessorAdapter;

    Button buttonGoChat;

    DatabaseReference databaseUsers;
    User currentUser;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("inHomeActivity", "inHomeActivity:success");
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pengout");

//        myViewPager = findViewById(R.id.main_tabs_pager);
//        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
//        myViewPager.setAdapter(myTabsAccessorAdapter);
//
//
//        myTabLayout = findViewById(R.id.main_tabs);
//        myTabLayout.setupWithViewPager(myViewPager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();
        Log.d("emeyil", email);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");


        //getting the reference of artists node
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        //getting chat button
        buttonGoChat =  findViewById(R.id.buttonGoChat);



        TextView username = findViewById(R.id.username);
        username.setText("Email: " + email);

        //adding an onclicklistener to button
        buttonGoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                finish();
                return true;
        }
        return false;
    }
}