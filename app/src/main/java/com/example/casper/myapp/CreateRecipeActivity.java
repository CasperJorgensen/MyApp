package com.example.casper.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.casper.myapp.helper.OnStartDragListener;
import com.example.casper.myapp.helper.SimpleItemTouchHelperCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener, OnStartDragListener, AdapterView.OnItemSelectedListener {
    private List<String> stepsList;

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ItemTouchHelper mItemTouchHelper;

    Context context;

    private Spinner spinner;

    private RecyclerListAdapter adapter;
    private IngredientsListAdapter iAdapter;

    private RecyclerView recyclerView, iRecyclerView;
    private Button addStep, addIngredient, saveRecipe;
    private EditText editTextStep, editTextIngredient, editTitle, editServings, editMeal;
    List<String> steps, ingredients;
    Map<String, List<String>> ingredientsList;
    private String TAG = "Woo";
    private ImageView imageView;
    private Uri filePath;
    private Uri picUri;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    final int CAMERA_CAPTURE = 1;

    private ChoosePhoto choosePhoto = null;

    private final int PICK_IMAGE_REQUEST = 71;
    private int PIC_CROP = 2;

    ImageView mImgCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView = (ImageView) findViewById(R.id.imageview_create_recipe);

        steps = new ArrayList<>();
        ingredients = new ArrayList<>();
        ingredientsList = new HashMap<String, List<String>>();

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
        editTitle = findViewById(R.id.edit_text_create_title);
        editServings = findViewById(R.id.edit_text_create_recipe_serves);
//        editMeal = findViewById(R.id.edit_text_create_recipe_meal);

        spinner = findViewById(R.id.meal_spinner);

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

        editTitle.setText(message);

        pickMeal();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_add_step) {
            if (!editTextStep.getText().toString().isEmpty()) {
                steps.add(editTextStep.getText().toString());
                adapter.notifyDataSetChanged();
                editTextStep.setText("");
            }
        }
        if (i == R.id.button_add_ingredient) {
            if (!editTextIngredient.getText().toString().isEmpty()) {
                ingredients.add(editTextIngredient.getText().toString());
                iAdapter.notifyDataSetChanged();
                editTextIngredient.setText("");
            }
        }
        if (i == R.id.button_save_recipe) {
            saveRecipe();
        }

    }

    private void pickMeal() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dummy_items, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void imageMethod() {
        String imagePath = mAuth.getCurrentUser().getDisplayName() + "/" + editTitle.getText().toString();
        try {
            if (choosePhoto.getCropImageUrl() != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child(imagePath);
                ref.putFile(choosePhoto.getCropImageUrl())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(CreateRecipeActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(CreateRecipeActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecipe() {
        if (!validateForm()) {
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        String imagePath = mAuth.getCurrentUser().getDisplayName() + "/" + editTitle.getText().toString();
        String user = mAuth.getCurrentUser().getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Date date = Calendar.getInstance().getTime();
        Course course = new Course(
                user,
                editTitle.getText().toString(),
                spinner.getSelectedItem().toString(),
                Integer.parseInt(editServings.getText().toString()),
                ingredients,
                steps,
                date,
                imagePath
        );

        db.collection(user)
                .document(editTitle.getText().toString())
                .set(course)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Created new course");
                imageMethod();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String title = editTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            editTitle.setError("Remember to put a title");
            valid = false;
        } else {
            editTitle.setError(null);
        }

        String serves = editServings.getText().toString();
        if (TextUtils.isEmpty(serves)) {
            editServings.setError("Remember to put servings");
            valid = false;
        } else {
            editServings.setError(null);
        }

//        if (ingredients.isEmpty()) {
//            editTextIngredient.setError("Remember to add ingredients");
//            valid = false;
//        } else {
//            editTextIngredient.setError(null);
//        }

        return valid;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_recipe_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                choosePhoto = new ChoosePhoto(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void takePicture() {
        choosePhoto = new ChoosePhoto(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ChoosePhoto.CHOOSE_PHOTO_INTENT) {
                if (data != null && data.getData() != null) {
                    choosePhoto.handleGalleryResult(data);
                } else {
                    choosePhoto.handleCameraResult(choosePhoto.getCameraUri());
                }
            }else if (requestCode == ChoosePhoto.SELECTED_IMG_CROP) {
                imageView.setImageURI(choosePhoto.getCropImageUrl());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ChoosePhoto.SELECT_PICTURE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                choosePhoto.showAlertDialog();
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}