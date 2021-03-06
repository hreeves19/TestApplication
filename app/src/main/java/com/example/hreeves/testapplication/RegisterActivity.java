package com.example.hreeves.testapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hreeves on 4/23/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText retypePasswordText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Setting editText values
        emailText = (EditText) findViewById(R.id.emailRegistration);
        passwordText = (EditText) findViewById(R.id.passwordRegistration);
        retypePasswordText = (EditText) findViewById(R.id.retypeRegistrationPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    //Purpose: To register a user with a valid email and password
    public void registerInformation(View v) {
        //Going to new activity
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String retypePassword = retypePasswordText.getText().toString().trim();

        //If text fields are empty or passwords do not match, it will stop and display an appropriate
        //message
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email....", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password....", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(retypePassword)) {
            Toast.makeText(this, "Please make sure retype password field is filled in.....", Toast.LENGTH_SHORT).show();
        }

        if(!password.equals(retypePassword)) {
            Toast.makeText(this, "Passwords do not match, please try again!", Toast.LENGTH_SHORT).show();
            return;
        }


        //if validations are passed, we will first show a progress bar
        progressDialog.setMessage("Registering user....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            //User is successfully registered
                            Toast.makeText(RegisterActivity.this, "Registered Successfully! Please verify your email by clicking " +
                                    "the link in the email we sent you!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            //Making sure that a user is signed in
                            if(user != null)
                            {
                                user.sendEmailVerification(); //Sending verification email
                            } else {
                                return;
                            }
                            editProfile();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Could not register successfully....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Purpose: To move to edit profile
    public void editProfile() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Intent intentToLeave = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentToLeave);
                        finish();
                        break;
                }
            }
        };

        //Showing alert dialog to decide if the user wants to edit their profile or not
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to edit your profile?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //Purpose: To go back to login
    public void goBackToLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
