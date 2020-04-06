package com.example.casper.myapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UnsignedUser extends AppCompatActivity {
    private Button signUpButton;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsigned_user);

        signUpButton = (Button) findViewById(R.id.signUp);
        logInButton = (Button) findViewById(R.id.logIn);
        final Intent signUpIntent = new Intent(this, SignUp.class);
        final Intent logInIntent = new Intent(this, LoginActivity.class);
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(signUpIntent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logInIntent);
            }
        });
    }
}
