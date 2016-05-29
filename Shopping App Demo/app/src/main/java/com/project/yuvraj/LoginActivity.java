package com.project.yuvraj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    TextView textSignup;
    Button btnLogin;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private static final int REQUEST_SIGNUP = 0;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//Get all the views from the  activity_login.xml --> done under init function
        init();


        //On Login Click
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Goto login() function
                login();
            }
        });

        //If signup button is clicked, perform following
        textSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start SignUp Activity
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(i, REQUEST_SIGNUP);
            }
        });
    }


    //once signup is completed, the result comes here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        //after signup, presently, after 5s delay subik() is called.
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }, 0);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void login() {

        if (!validate()) {
            //if the entered details are not valid, goto the following function
            onLoginFailed();
            return;
        }
        //else if the endered data is valid, perform following
        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        //To check login details from database
        String pswd = databaseHelper.searchPass(email);
        if (password.equals(pswd)) {

            //If the entered password is the same that is stored in the database
            // Login is Successful
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {

                    onLoginSuccess();
                    progressDialog.dismiss();
                }
            }, 3000);
        } else {
            //either the entered username or password doesnt match
            Toast.makeText(LoginActivity.this, "Username or Password Don't Match", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            btnLogin.setEnabled(true);
        }


    }


    private void init() {
        editEmail = (EditText) findViewById(R.id.input_email);
        editPassword = (EditText) findViewById(R.id.input_password);
        textSignup = (TextView) findViewById(R.id.link_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        myApplication = (MyApplication) getApplication();
    }


    public boolean validate() {

        // This function is to check whether the entered email address and password are in the prescribed format or not

        boolean valid = true;

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editPassword.setError("The Password should have 4 to 10 alphanumeric characters");
            valid = false;
        } else {
            editPassword.setError(null);
        }
        return valid;
    }


    public void onLoginSuccess() {
        btnLogin.setEnabled(true);

        //Get the email and name of the user
        String email = editEmail.getText().toString();
        String name = databaseHelper.searchDetails(email);
        String id = databaseHelper.getUserId(email,name);

        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        myApplication.saveToken("EmailS",email);
        myApplication.saveToken("NameS",name);
        myApplication.saveToken("Id", id);
        startActivity(intent);
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }


}