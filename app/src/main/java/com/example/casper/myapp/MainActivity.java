package com.example.casper.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private int SIGN_IN_REQUEST_CODE = 1;
    private String TAG;
    private View view;
    public static final String EXTRA_MESSAGE = "com.example.casper.myapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipe();
            }
        });

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void createRecipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new recipe");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        builder.setPositiveButton("Create recipe", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final String m_Text = input.getText().toString();
                Map<String, Object> data = new HashMap<>();
                data.put("recipeName", m_Text);
                data.put("more", "ofsdfd");
                db.collection("users").document(mAuth.getCurrentUser().getDisplayName().toString()).collection(m_Text).document(m_Text).set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Intent intent = new Intent(MainActivity.this, CreateRecipeActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, m_Text);
                                startActivity(intent);
                            }
                        });
//                DocumentReference userDocRef = db.document("users/"+mAuth.getUid());
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

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, UnsignedUser.class);
            startActivity(intent);
//            startActivityForResult(
//                    AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .build(),
//                    SIGN_IN_REQUEST_CODE
//            );
        }
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
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.deleteUser:
                deleteUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Intent intent = new Intent(this, UnsignedUser.class);
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted");
                            startActivity(intent);
                        }
                    }
                });
    }
}
