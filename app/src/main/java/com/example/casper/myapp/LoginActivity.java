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
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "yes";
    private EditText txtEmail;
    private EditText txtPassword;
    private Button loginButton;
    private FirebaseAuth mAuth;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button)findViewById(R.id.loginButton);
        txtEmail = (EditText) findViewById(R.id.emailLoginText);
        txtPassword = (EditText) findViewById(R.id.passwordLoginText);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        findViewById(R.id.loginButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginButton) {
            signIn(txtEmail.getText().toString(), txtPassword.getText().toString());
        }
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        final Intent intent = new Intent(this, MainActivity.class);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:succes");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(intent);
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.METHOD, "method");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Required");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        String password = txtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Required");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }
}