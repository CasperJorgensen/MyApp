package com.example.casper.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editRecipeTitle;
    private EditText recipeAddIngredient;
    private Button addIngredientButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        addIngredientButton = (Button) findViewById(R.id.addIngredientButton);
        editRecipeTitle = (EditText) findViewById(R.id.editRecipeTitle);
        recipeAddIngredient = (EditText) findViewById(R.id.recipeAddIngredient);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.addIngredientButton).setOnClickListener(this);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        setTitle(message);
        editRecipeTitle.setText(message);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.addIngredientButton) {
            addIngredient();
        }
    }

    private void addIngredient() {
        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("ingredients", Arrays.asList(recipeAddIngredient.getText().toString()));
        db.collection("users")
                .document(mAuth.getCurrentUser().getDisplayName().toString())
                .collection(message)
                .document(message)
                .set(data, SetOptions.merge());


    }
}
