package com.example.langshareapp.repositories;

import android.util.Log;

import com.example.langshareapp.utils.LangShareUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseFirestoreRepository {
    private static FirebaseFirestoreRepository instance;
    private FirebaseFirestore db;
    private CollectionReference usersCollection;
    private LangShareUser currentUser;
    private FirebaseAuthRepository authRepository;
    private FirebaseUser mAuthUser;

    public interface UserRetrievedCallback {
        void onCallback(LangShareUser user);
    }

    public interface UserSavedCallback {
        void onCallback(boolean status);
    }

    public interface UserFeedCallback {
        void onCallback(List<LangShareUser> users);
    }

    private FirebaseFirestoreRepository() {
        db = FirebaseFirestore.getInstance();
        usersCollection = db.collection("users");
        authRepository = FirebaseAuthRepository.getInstance();
        mAuthUser = authRepository.getCurrentUser();
    }

    public static FirebaseFirestoreRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseFirestoreRepository();
        }
        return instance;
    }

    public void getCurrentUser(UserRetrievedCallback callback) {
        if (currentUser != null) {
            callback.onCallback(currentUser);
        } else {
            getUserByMAuthId(mAuthUser.getUid(), callback);
        }
    }

    public void setCurrentUser(LangShareUser user) {
        currentUser = user;
    }

    public void getUserByMAuthId(String mAuthId, UserRetrievedCallback callback) {
        usersCollection
                .whereEqualTo("auth_id", mAuthId)
                .get()
                .addOnCompleteListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> res = queryDocumentSnapshots.getResult().getDocuments();
                    if (res.size() > 0) {
                        Log.i("FirestoreRepo", "Got user by mAuthId" + res.get(0).getData());
                        DocumentSnapshot userDoc = res.get(0);
                        setCurrentUser(LangShareUser.fromDocument(userDoc));
                        callback.onCallback(LangShareUser.fromDocument(userDoc));
                    } else
                        callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreRepo", "Error getting user by mAuthId", e);
                    callback.onCallback(null);
                });
    }

    public void saveUser(LangShareUser user, UserSavedCallback callback) {
        try {
            usersCollection.add(user.toMap())
                    .addOnSuccessListener(documentReference -> {
                        Log.i("FirestoreRepo", "User added with ID: " + documentReference.getId());
                        callback.onCallback(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreRepo", "Error adding user", e);
                        callback.onCallback(false);
                    });
        } catch (Exception e) {
            Log.e("FirestoreRepo", "Error adding user", e);
        }
    }

    public void getUsers(LangShareUser user, UserFeedCallback callback) {
        try {
            Log.i("FirestoreRepo", "Getting users");

            Task firstQuery = usersCollection
                    .whereArrayContainsAny("languages_known", user.getLanguagesLearning())
                    .get();

            Task secondQuery = usersCollection
                    .whereArrayContainsAny("languages_learning", user.getLanguagesKnown())
                    .get();

            Task combinedTask = Tasks.whenAllSuccess(firstQuery, secondQuery).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                @Override
                public void onSuccess(List<Object> results) {
                    List<DocumentSnapshot> firstQueryResults = ((QuerySnapshot) results.get(0)).getDocuments();
                    List<DocumentSnapshot> secondQueryResults = ((QuerySnapshot) results.get(1)).getDocuments();

                    // Merge the results
                    List<DocumentSnapshot> mergedResults = new ArrayList<>(firstQueryResults);
                    for (DocumentSnapshot doc : secondQueryResults) {
                        if (!mergedResults.contains(doc)) {
                            mergedResults.add(doc);
                        }
                    }

                    callback.onCallback(LangShareUser.fromDocumentList(mergedResults));
                }
            }).addOnFailureListener(e -> {
                Log.e("FirestoreRepo", "Error getting users", e);
                callback.onCallback(null);
            });
        } catch (Exception e) {
            Log.e("FirestoreRepo", "Error fetching users", e);
        }
    }

}
