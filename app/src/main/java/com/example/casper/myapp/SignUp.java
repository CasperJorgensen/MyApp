package com.example.casper.myapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.perf.metrics.Trace;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        txtUsername = (EditText) findViewById(R.id.userName);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        signUpButton = (Button) findViewById(R.id.signUpUserButton);

        findViewById(R.id.signUpUserButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @AddTrace(name = "createAccount", enabled = true)
    private void createAccount(final String email, String password) {
        Trace myTrace = FirebasePerformance.getInstance().newTrace("createAccountTrace");
        myTrace.start();
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        final Intent intent = new Intent(this, MainActivity.class);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser username = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(txtUsername.getText().toString()).build();
                            username.updateProfile(profileUpdates);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", txtUsername.getText().toString());
                            user.put("email", txtEmail.getText().toString());


                            db.collection(txtUsername.getText().toString())
                                    .document("user")
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });

                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
        myTrace.stop();
    }

    private boolean validateForm() {
        boolean valid = true;

        String userName = txtUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            txtUsername.setError("Required");
            valid = false;
        } else {
            txtUsername.setError(null);
        }

        String email = txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Required");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        String password = txtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Required.");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signUpUserButton) {
            createAccount(txtEmail.getText().toString(), txtPassword.getText().toString());
//            Intent intent = new Intent(SignUp.this, UnsignedUser.class);
//            startActivity(intent);
        }
    }
}
