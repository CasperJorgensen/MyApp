package com.example.casper.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateRecipeActivity extends AppCompatActivity {
    private EditText editRecipeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        editRecipeTitle = (EditText) findViewById(R.id.editRecipeTitle);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        setTitle(message);
        editRecipeTitle.setText(message);
    }


}
