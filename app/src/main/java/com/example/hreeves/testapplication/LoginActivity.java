package com.example.hreeves.testapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

/**
 * Created by hreeves on 4/23/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText passwordText;
    private EditText emailText;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Getting firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing progressDialog
        progressDialog = new ProgressDialog(this);

        //Getting information from activity
        passwordText = (EditText) findViewById(R.id.password);
        emailText = (EditText) findViewById(R.id.emailAddress);
    }

    //Purpose: To signin a valid user
    public void submit(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //If email or password are empty
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email....", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password....", Toast.LENGTH_SHORT).show();
            return;
        }

        //showing progressDialog loader
        progressDialog.setMessage("Attempting to sign in....");
        progressDialog.show();

        //Signing in with firebase
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email or password is incorrect. Please try again!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Purpose: To move to the register activity
    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
