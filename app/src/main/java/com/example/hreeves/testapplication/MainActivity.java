package com.example.hreeves.testapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by hreeves on 4/23/2017.
 */

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //User already signed in
        if(firebaseAuth.getCurrentUser() != null) {
            MessageBox(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " has signed in!");
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Purpose: To display a toast message
    public void MessageBox(String input) {

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    //Purpose: To move to the chat room activity
    public void moveToMessenger(View v) {
        Intent intent = new Intent(this, ChatRoomListActivity.class);
        startActivity(intent);
    }

    //Purpose: To move to the game activity
    public void moveToGame(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    //Purpose: To move to the login activity
    public void goToLogin(View v) {
        firebaseAuth.signOut(); //Signing user out
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //Purpose: To move to the phonebook activity
    public void goToPhone(View v) {
        Intent intent = new Intent(this, PhoneBookActivity.class);
        startActivity(intent);
    }

    //Purpose: To move to the emergency activity
    public void goToEmergency(View v) {
        Intent intent = new Intent(this, EmergencyActivity.class);
        startActivity(intent);
    }
}
