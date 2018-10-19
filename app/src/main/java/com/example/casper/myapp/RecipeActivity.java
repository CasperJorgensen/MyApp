package com.example.casper.myapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private List<Course> recipeList;
    private List<Course> ingredientsList;
    private List<Course> stepsList;

    private String TAG = "Super";
    private FirebaseAuth mAuth;

    private RecipeAdapter adapter;
    private IngredientsAdapter iAdapter;
    private StepsAdapter sAdapter;

    private RecyclerView iRecyclerView;
    private RecyclerView sRecyclerView;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getDisplayName();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        setTitle(message);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recipe);
        iRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recipe_ingredients);
        sRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recipe_steps);

        recipeList = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();

        adapter = new RecipeAdapter(this, recipeList);
        iAdapter = new IngredientsAdapter(this, ingredientsList);
        sAdapter = new StepsAdapter(this, stepsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager iLayoutManager = new GridLayoutManager(this, 1);
        iRecyclerView.setLayoutManager(iLayoutManager);
        iRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        iRecyclerView.setItemAnimator(new DefaultItemAnimator());
        iRecyclerView.setAdapter(iAdapter);

        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 1);
        sRecyclerView.setLayoutManager(sLayoutManager);
        sRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        sRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sRecyclerView.setAdapter(sAdapter);
        prepareRecipe();
    }

    private void prepareRecipe() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String currentUser = mAuth.getCurrentUser().getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(currentUser).document(message);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Course course = documentSnapshot.toObject(Course.class);
                recipeList.add(course);
                ingredientsList.add(course);
                adapter.notifyDataSetChanged();
                iAdapter.notifyDataSetChanged();
                stepsList.add(course);
                sAdapter.notifyDataSetChanged();
            }
        });
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
