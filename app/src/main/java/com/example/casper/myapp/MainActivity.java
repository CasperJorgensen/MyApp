package com.example.casper.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private int SIGN_IN_REQUEST_CODE = 1;
    private String TAG = "Stringy";
    private View view;
    public static final String EXTRA_MESSAGE = "com.example.casper.myapp";

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private CourseAdapter courseAdapter;
    private List<Album> albumList;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseRemoteConfig.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, UnsignedUser.class);
            startActivity(intent);
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        courseList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        courseAdapter = new CourseAdapter(this, courseList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);

        prepareRecipes();

//        try {
//            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipe();
            }
        });

//        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pullToRefresh.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, UnsignedUser.class);
            startActivity(intent);
        }
        mAuth.getCurrentUser().getDisplayName();
    }

//    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(" ");
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.setExpanded(true);
//
//        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle(mAuth.getCurrentUser().getDisplayName().toString());
//                    isShow = true;
//                } else if (isShow){
//                    collapsingToolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
//    }

    private void prepareRecipes() {
        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Course course = document.toObject(Course.class);
                                courseList.add(course);
                                courseAdapter.notifyDataSetChanged();
                            }
                            Log.d(TAG, courseList.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
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

    private void createRecipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new recipe");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("Create recipe", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String m_Text = input.getText().toString();
                Intent intent = new Intent(MainActivity.this, CreateRecipeActivity.class);
                intent.putExtra(EXTRA_MESSAGE, m_Text);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                mAuth.signOut();
                startActivity(new Intent(this, UnsignedUser.class));
                return true;
            case R.id.deleteUser:
                deleteUser();
                startActivity(new Intent(this, UnsignedUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String deletedUser = mAuth.getCurrentUser().getDisplayName().toString();
        final Intent intent = new Intent(this, UnsignedUser.class);
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Log.d(TAG, "User account deleted");
                            startActivity(intent);
                            db.collection("users")
                                    .document(deletedUser)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot succesfully deleted!");
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });
                        }
                    }
                });
    }
}
