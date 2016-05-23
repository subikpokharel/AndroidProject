package com.project.yuvraj;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.parsing.Details;

public class SignUpActivity extends AppCompatActivity {

    EditText editName, editEmail, editPassword, editConfirm;
    Button btnCreate;
    TextView textLogin;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        //On Create Account Clicked
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        //On Already A user Clicked
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }






    public void signup() {

        if (!validate()){
            onSignupFailed();
            return;
        }
        btnCreate.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

//On Signup Valid, Insert into database
        Details d = new Details();
        d.setUname(name);
        d.setEmail(email);
        d.setPassword(password);
        databaseHelper.insertDetails(d);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {

        //Returns to the LoginActivity

        btnCreate.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        btnCreate.setEnabled(true);
    }




    public boolean validate(){
        boolean valid = true;

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String pass2 = editConfirm.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editPassword.setError("Password Length should be between 4 to 10 alphanumeric characters");
            valid = false;
        } else {
            editPassword.setError(null);
        }

        if (!password.equals(pass2)){
            Toast.makeText(SignUpActivity.this, "Password Don't Match",Toast.LENGTH_LONG).show();
            valid = false;
        }


        return valid;
    }





    public void init() {

        editName = (EditText) findViewById(R.id.input_name);
        editEmail = (EditText) findViewById(R.id.input_email);
        editPassword = (EditText) findViewById(R.id.input_password);
        textLogin = (TextView) findViewById(R.id.link_login);
        btnCreate = (Button) findViewById(R.id.btn_signup);
        editConfirm = (EditText) findViewById(R.id.input_Conformpassword);
    }
}
