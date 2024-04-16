package com.example.langshareapp.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository {
    private static FirebaseAuthRepository instance;
    private FirebaseAuth mAuth;

    public static FirebaseAuthRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthRepository();
        }
        return instance;
    }

    private FirebaseAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public void signOut() {
        mAuth.signOut();
    }

    // Add more methods as needed for authentication tasks, e.g., sign-in, password reset, etc.
}
