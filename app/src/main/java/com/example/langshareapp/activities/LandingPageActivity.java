package com.example.langshareapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.langshareapp.MainActivity;
import com.example.langshareapp.R;
import com.example.langshareapp.repositories.FirebaseAuthRepository;
import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {
    private FirebaseAuthRepository authRepository;
    private FirebaseUser user;
    private FirebaseFirestoreRepository firestoreRepository;
    private ActivityResultLauncher<Intent> signInLauncher;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        firestoreRepository = FirebaseFirestoreRepository.getInstance();
        authRepository = FirebaseAuthRepository.getInstance();

        loginButton = findViewById(R.id.login_button);

        signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                this::onSignInResult
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        user = authRepository.getCurrentUser();
        updateUI(user);

        loginButton.setOnClickListener(v -> {
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch((signInIntent));
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            firestoreRepository.getUserByMAuthId(currentUser.getUid(), user -> {
                Log.i("Authlog", "User from firestore: " + user);
                Intent intent;
                if (user == null) {
                    intent = new Intent(this, SignUpActivity.class);
                } else {
                    intent = new Intent(this, MainActivity.class);
                }
                Log.i("Authlog", "Starting activity: " + intent);
                startActivity(intent);
                finish();
            });
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            user = authRepository.getCurrentUser();
            updateUI(user);
        } else {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
        }
    }
}