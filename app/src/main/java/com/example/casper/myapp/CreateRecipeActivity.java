package com.example.casper.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView recipeIngredients;
    private TextView recipeSteps;
    private EditText editRecipeTitle;
    private EditText editRecipeMeal;
    private EditText recipeAddIngredient;
    private EditText recipeAddStep;
    private EditText noOfServings;
    private Button addIngredientButton;
    private Button addStepButton;
    private Button saveRecipe;
    private FirebaseAuth mAuth;
    private String TAG = "Ohboy";
    private List<String> ingredientsList;
    private List<String> stepsList;

    private int ingredientCount;
    private int stepsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        addIngredientButton = (Button) findViewById(R.id.addIngredientButton);
        addStepButton = (Button) findViewById(R.id.addStepButton);
        saveRecipe = (Button) findViewById(R.id.save_recipe);

        editRecipeTitle = (EditText) findViewById(R.id.editRecipeTitle);
        editRecipeMeal = (EditText) findViewById(R.id.editRecipeMeal);

        recipeAddIngredient = (EditText) findViewById(R.id.recipeAddIngredient);
        recipeAddStep = (EditText) findViewById(R.id.recipeAddStep);
        noOfServings = (EditText) findViewById(R.id.no_of_servings);

        recipeIngredients = (TextView) findViewById(R.id.ingredientsTextView);
        recipeSteps = (TextView) findViewById(R.id.stepsTextView);

        ingredientsList = new ArrayList<String>();
        stepsList = new ArrayList<String>();

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.addIngredientButton).setOnClickListener(this);
        findViewById(R.id.addStepButton).setOnClickListener(this);
        findViewById(R.id.save_recipe).setOnClickListener(this);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        setTitle("Creating recipe: " + message);
        editRecipeTitle.setText(message);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.addIngredientButton) {
            addIngredient();
        }
        if (i == R.id.addStepButton) {
            addStep();
        }
        if (i == R.id.save_recipe) {
            saveRecipe();
        }
    }

    private void saveRecipe() {
//        if (!validateForm()) {
//            return;
//        }
        int servings = Integer.parseInt(noOfServings.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUser = mAuth.getCurrentUser().getDisplayName();
        Course course = new Course(editRecipeTitle.getText().toString(),
                editRecipeMeal.getText().toString(),
                servings,
                ingredientsList,
                stepsList);
        db.collection(mAuth.getCurrentUser().getDisplayName())
            .document(editRecipeTitle.getText().toString())
            .set(course);


    }

    private void addStep() {
//        if (!validateForm()) {
//            return;
//        }
        stepsList.add(recipeAddStep.getText().toString());
        recipeSteps.append((stepsCount + 1) + " " + stepsList.get(stepsCount) + "\n");
        stepsCount++;
        recipeAddStep.setText("");

//        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference stepArray = db.collection(mAuth.getCurrentUser().getDisplayName().toString())
//                .document(message);
////                .collection(message)
////                .document(message);
//
//        final Map<String, Object> data = new HashMap<>();
//        if (!recipeAddStep.getText().toString().trim().isEmpty()) {
//            data.put("steps", Arrays.asList(recipeAddStep.getText().toString()));
//            stepArray.update("steps", FieldValue.arrayUnion(recipeAddStep.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    recipeAddStep.setText("");
//                }
//            });
//        }
    }

    private void addIngredient() {
//        if (!validateForm()) {
//            return;
//        }
        ingredientsList.add(recipeAddIngredient.getText().toString());
        recipeIngredients.append(" - " + ingredientsList.get(ingredientCount) + "\n");
        ingredientCount++;
        recipeAddIngredient.setText("");


//        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference ingredientArray = db.collection(mAuth.getCurrentUser().getDisplayName().toString())
//                .document(message);
////                .collection(message)
////                .document(message);
//
//        final Map<String, Object> data = new HashMap<>();
//        if (!recipeAddIngredient.getText().toString().trim().isEmpty()) {
//            data.put("ingredients", Arrays.asList(recipeAddIngredient.getText().toString()));
//            ingredientArray.update("ingredients", FieldValue.arrayUnion(recipeAddIngredient.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    recipeAddIngredient.setText("");
//                }
//            });
//        }
    }

    private void updateIngredientList() {
        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection(mAuth.getCurrentUser().getDisplayName().toString())
                .document("user")
                .collection(message)
                .document(message);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Log.d(TAG, "Current data: " + documentSnapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String addIngredient = recipeAddIngredient.getText().toString();
        if (TextUtils.isEmpty(addIngredient)) {
            recipeAddIngredient.setError("Remember to add ingredient!");
            valid = false;
        } else {
            recipeAddIngredient.setError(null);
        }

        String addStep = recipeAddStep.getText().toString();
        if (TextUtils.isEmpty(addStep)) {
            recipeAddStep.setError("Required");
            valid = false;
        } else {
            recipeAddStep.setError(null);
        }

        return valid;
    }
}
