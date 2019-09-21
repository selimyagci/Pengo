package com.example.pengout;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;


    private Boolean isLogin = false;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        rootRef = FirebaseDatabase.getInstance().getReference();

        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Log.d("Firebasejan", "FirebaseUser is null");
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//        firebaseUser = firebaseAuth.getCurrentUser();


        //initializing views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextView already = findViewById(R.id.reg_text);
        Button buttonSignup = findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        already.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
//                ProgressDialog progressBar = ProgressDialog.show(RegisterActivity.this, "Title", "Directing to login screen ");
//                progressBar.setCancelable(true);

                // buttonlarin yazilarini degsitiriyorum

//                //initializing views
//                editTextEmail = findViewById(R.id.editTextEmail);
//                editTextPassword = findViewById(R.id.editTextPassword);

                // disappear the already have account text
                TextView already = findViewById(R.id.reg_text);
                already.setVisibility(TextView.INVISIBLE);

                // change button text
                Button buttonLogin = findViewById(R.id.buttonSignup);
                buttonLogin.setText("Login");
                isLogin = true;

                return false;
            }
        });
    }


    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this,"Password must be at least 6 characters",Toast.LENGTH_LONG).show();
            return;

        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String currentUserID = firebaseAuth.getCurrentUser().getUid();
                            rootRef.child("users").child(currentUserID).setValue("");

                            Toast.makeText(RegisterActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, InformationActivity.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                            progressDialog.dismiss();
                            return;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("createUserWithEmail", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }


    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("signInWithEmail", "signInWithEmail:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
//                            Log.d("afterStartActivity", "afterStartActivity:success");
                            progressDialog.dismiss();
                            return;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInWithEmail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }
    @Override
    public void onClick(View view) {
        //calling register method on click
        if(isLogin == false){
            registerUser();
        }
        if(isLogin == true){
            loginUser();
        }

    }


}