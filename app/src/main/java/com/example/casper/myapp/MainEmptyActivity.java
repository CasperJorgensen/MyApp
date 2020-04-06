package com.example.casper.myapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainEmptyActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty);

        mAuth = FirebaseAuth.getInstance();
        Intent activityIntent;

        if (mAuth.getCurrentUser() != null) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, UnsignedUser.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
