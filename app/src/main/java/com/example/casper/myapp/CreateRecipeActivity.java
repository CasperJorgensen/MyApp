package com.example.casper.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.casper.myapp.helper.OnStartDragListener;
import com.example.casper.myapp.helper.SimpleItemTouchHelperCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener, OnStartDragListener {
    private List<String> stepsList;

    private FirebaseAuth mAuth;

    private ItemTouchHelper mItemTouchHelper;

    Context context;

    private RecyclerListAdapter adapter;
    private IngredientsListAdapter iAdapter;

    private RecyclerView recyclerView, iRecyclerView;
    private Button addStep, addIngredient, saveRecipe;
    private EditText editTextStep, editTextIngredient, editTitle, editServings;
    List<String> steps, ingredients;
    private String TAG = "Woo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        mAuth = FirebaseAuth.getInstance();


        steps = new ArrayList<>();
        ingredients = new ArrayList<>();

        adapter = new RecyclerListAdapter(context, steps, this);
        iAdapter = new IngredientsListAdapter(context, ingredients, this);

        recyclerView = findViewById(R.id.recycler_view_create_recipe_steps);
        iRecyclerView = findViewById(R.id.recycler_view_create_recipe_ingredients);

        addStep = findViewById(R.id.button_add_step);
        addIngredient = findViewById(R.id.button_add_ingredient);
        saveRecipe = findViewById(R.id.button_save_recipe);
        findViewById(R.id.button_add_step).setOnClickListener(this);
        findViewById(R.id.button_add_ingredient).setOnClickListener(this);
        findViewById(R.id.button_save_recipe).setOnClickListener(this);

        editTextStep = findViewById(R.id.edit_text_step);
        editTextIngredient = findViewById(R.id.edit_text_ingredients);
        editTitle = findViewById(R.id.recipe_card_create_title);
        editServings = findViewById(R.id.edit_text_create_recipe_serves);

        iRecyclerView.setHasFixedSize(true);
        iRecyclerView.setAdapter(iAdapter);
        iRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper.Callback iCallback = new SimpleItemTouchHelperCallback(iAdapter);
        mItemTouchHelper = new ItemTouchHelper(iCallback);
        mItemTouchHelper.attachToRecyclerView(iRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_add_step) {
            steps.add(editTextStep.getText().toString());
            adapter.notifyDataSetChanged();
            editTextStep.setText("");
        }
        if (i == R.id.button_add_ingredient) {
            ingredients.add(editTextIngredient.getText().toString());
            iAdapter.notifyDataSetChanged();
            editTextIngredient.setText("");
        }
        if (i == R.id.button_save_recipe) {
            saveRecipe();
        }

    }

    private void saveRecipe() {
        Course course = new Course(
                mAuth.getCurrentUser().getDisplayName(),
                editTitle.getText().toString(),

        );
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}