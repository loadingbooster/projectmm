package com.px.track.projectmm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class login extends AppCompatActivity {
    private static final String apiurl="http:// 192.168.208.1/aws.com/login_maker.php";
    //Declaring the variables for editfields username and password

    EditText usernamefield, passwordfield;
    //These are the altert text below the edittext to alert the user of wrong username or password
    TextView uidalert,passalert;
    //The eye icon used to view the password
    ImageView viewpass;
    //login button
    Button login;
    //the load bar used to show the user that the data is being processed
    ProgressBar loginload;
    int visible = 0;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        usernamefield = findViewById(R.id.usernamefield);
        passwordfield = findViewById(R.id.passwordfield);

        uidalert = findViewById(R.id.uidalert);
        passalert = findViewById(R.id.passalert);

        viewpass = findViewById(R.id.viewpass);

        login = findViewById(R.id.loginbtn);

        loginload = findViewById(R.id.progressBarlogin);

        //setting the alter text to invisible initially, they will be after after an error has been encountered.
        uidalert.setVisibility(View.INVISIBLE);
        passalert.setVisibility(View.INVISIBLE);
        //set the login button to invisible initially
        login.setEnabled(false);
        loginload.setVisibility(View.INVISIBLE);

        ///button click listener for viewing/unviewing password

        viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visible == 0){
                    passwordfield.setTransformationMethod(null);
                    visible =1;
                }else if (visible == 1){
                    passwordfield.setTransformationMethod(new PasswordTransformationMethod());
                    visible = 0;
                }

            }
        });

        /////////this event watched for text chagnes that are occuring in uid field and password field and view/show the login button accordingly
        ///////
        usernamefield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(usernamefield.getText().toString().trim().length() > 0){
                    username = usernamefield.getText().toString();
                    if(passwordfield.getText().toString().trim().length() > 0){
                        login.setEnabled(true);
                    }
                }
                else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////
        /////////this event watched for text chagnes that are occuring in uid field and password field and view/show the login button accordingly
        passwordfield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordfield.getText().toString().trim().length() > 0){
                    password = passwordfield.getText().toString();
                    viewpass.setVisibility(View.VISIBLE);
                    if(usernamefield.getText().toString().trim().length() > 0){
                        login.setEnabled(true);





                    }
                }
                else{
                    login.setEnabled(false);
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


                ////////////////////#################    Don't change this below this####################//////
                //hiding the password error below password field
                passerror(false);
                ///hiding the username error below username field
                uierror(false);
                ///making the loadbar visible///
                loginload.setVisibility(View.VISIBLE);


                //
                //
                String qry="?usernamefield="+usernamefield.getText().toString().trim()+"&passwordfield="+passwordfield.getText().toString().trim();

                class dbprocess extends AsyncTask<String,Void,String>
                {
                    @Override
                    protected  void onPostExecute(String data)
                    {
                        if(data.equals("found"))
                        {
                            SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("uname",usernamefield.getText().toString());
                            editor.commit();

                        }
                        else
                        {
                            usernamefield.setText("");
                            passwordfield.setText("");

                        }
                    }
                    @Override
                    protected String doInBackground(String... params)
                    {
                        String furl=params[0];

                        try
                        {
                            java.net.URL url=new URL(furl);
                            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                            return br.readLine();

                        }catch (Exception ex)
                        {
                            return ex.getMessage();
                        }
                    }
                }

                dbprocess obj=new dbprocess();
                obj.execute(apiurl+qry);

            }

            /////////////////////////############# Don't change code above this##############################////
                /////write your backed code below this/////
                ////call functions passerror and uierror and pass true as parameter to show password error and userid error respectively.
                ///pass in true to the function  "userverified"  to login the user after successful db verification.\



        });

    }
    
    //call this function when the username is wrong
    private void uierror(boolean result){
        if(result){
            uidalert.setVisibility(View.VISIBLE);
            loginload.setVisibility(View.INVISIBLE);
        }
        else{
            uidalert.setVisibility(View.INVISIBLE);
            loginload.setVisibility(View.INVISIBLE);
        }
    }
    //call this function when the password is wrong
    private void passerror(boolean result){
        if(result){
            passalert.setVisibility(View.VISIBLE);
            loginload.setVisibility(View.INVISIBLE);
        }
        else{
            passalert.setVisibility(View.INVISIBLE);
            loginload.setVisibility(View.INVISIBLE);
        }
    }
    //this function shall be called after the user has been verified. This function will login the user to the application.
    private void userverified(boolean verified){
        if(verified){
            Toast.makeText(this, "User Verified", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,dashboard.class);

            finish();

        }
    }

}