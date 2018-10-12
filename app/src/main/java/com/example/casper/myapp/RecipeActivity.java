package com.example.casper.myapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    private TextView recipeTitle;
    private String TAG = "Super";
    private RecyclerView recipeIngredients;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeIngredients = findViewById(R.id.recipeIngredients);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipeIngredients);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new CourseAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection(mAuth.getCurrentUser().getDisplayName().toString())
                .document("user")
                .collection("bedst")
                .document("bedst");

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ArrayList<String> arrList = new ArrayList<String>();
                    arrList = (ArrayList) documentSnapshot.get("ingredients");
//                    mAdapter = new CourseAdapter(arrList);

                    for (int i = 0; i <arrList.size(); i++) {
                        recipeTitle.setText(arrList.get(i)+"ok\n");
                    }
                }
            }
        });

        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        DocumentReference docRef = db.collection(mAuth.getCurrentUser().getDisplayName().toString())
                .document("user")
                .collection("bedst")
                .document("bedst");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        recipeTitle.setText(document.getString("recipeName"));
                    }
                }
            }
        });
    }
}
