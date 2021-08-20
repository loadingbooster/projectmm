package com.px.track.projectmm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    //Declaring the variables for editfields username and password
    EditText username,password;
    //These are the altert text below the edittext to alert the user of wrong username or password
    TextView uidalert,passalert;
    //The eye icon used to view the password
    ImageView viewpass;
    //login button
    Button login;
    //the load bar used to show the user that the data is being processed
    ProgressBar loginload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        username = findViewById(R.id.usernamefield);
        password = findViewById(R.id.passwordfield);

        uidalert = findViewById(R.id.uidalert);
        passalert = findViewById(R.id.passalert);

        viewpass = findViewById(R.id.viewpass);

        login = findViewById(R.id.loginbtn);

        loginload = findViewById(R.id.progressBarlogin);

        //setting the alter text to invisible initially, they will be after after an error has been encountered.
        uidalert.setVisibility(View.INVISIBLE);
        passalert.setVisibility(View.INVISIBLE);
        //set the login button to invisible initially
        login.setVisibility(View.INVISIBLE);
        loginload.setVisibility(View.INVISIBLE);
        viewpass.setVisibility(View.INVISIBLE);

        viewpass.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        password.setTransformationMethod(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        password.setTransformationMethod(new PasswordTransformationMethod());
                        break;
                }
                return true;
            }
        });

        /////////this event watched for text chagnes that are occuring in uid field and password field and view/show the login button accordingly
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().trim().length() > 0){
                    if(password.getText().toString().trim().length() > 0){
                        login.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    login.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////this event watched for text chagnes that are occuring in uid field and password field and view/show the login button accordingly
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password.getText().toString().trim().length() > 0){
                    viewpass.setVisibility(View.VISIBLE);
                    if(username.getText().toString().trim().length() > 0){
                        login.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    login.setVisibility(View.INVISIBLE);
                    viewpass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //loginbutton click event. this is called when the login button is pressed
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passalert.setVisibility(View.INVISIBLE);
                uidalert.setVisibility(View.INVISIBLE);
                loginload.setVisibility(View.VISIBLE);
                ////write ur backed code below

                userverified(true);
            }
        });

    }
    //call this function when the username is wrong
    private void uierror(boolean result){
        if(result){
            uidalert.setVisibility(View.INVISIBLE);
        }
    }
    //call this function when the password is wrong
    private void passerror(boolean result){
        if(result){
            passalert.setVisibility(View.INVISIBLE);
        }
    }
    //this function shall be called after the user has been verified. This function will login the user to the application.
    private void userverified(boolean verified){
        if(verified){
            Toast.makeText(this, "User Verified", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,dashboard.class);
            startActivity(intent);
        }
    }

}