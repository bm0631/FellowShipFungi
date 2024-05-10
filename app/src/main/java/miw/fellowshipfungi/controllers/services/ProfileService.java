package miw.fellowshipfungi.controllers.services;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import miw.fellowshipfungi.models.profile.ProfileData;

public class ProfileService extends BaseService {
    private static String LOG_TAG="ProfileService";
    private static ProfileService instance;


    private ProfileService() {
        super();
    }

    public static ProfileService getInstance() {
        if (instance == null) {
            instance = new ProfileService();
        }
        return instance;
    }

    public void getProfileData(final OnProfileDataListener listener) {
        ProfileData profileData = new ProfileData();


        profileData.setUsername(this.getName());


        getLengthCollection(length -> {
            profileData.setLengthCollection(length);

            // Obtener racha actual
            getCurrentStreak(streak -> {
                profileData.setStreak(streak);

                // Obtener mejor resultado
                getBestResult(bestResult -> {
                    profileData.setBestResult(bestResult);

                    // Llamar al listener con los datos completos
                    listener.onProfileDataLoaded(profileData);
                });
            });
        });
    }

    public void getCurrentStreak(final OnGetCurrentStreakListener listener) {
        DocumentReference userRef = db.collection(COLLECTION_PROFILE).document(this.getUserId());
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Long streak = document.getLong("streak");
                if (streak != null) {
                    int streakLength = streak.intValue();
                    listener.onGetCurrentStreak(streakLength);
                } else {
                    listener.onGetCurrentStreak(0);
                }
            } else {
                this.handleFirestoreError(LOG_TAG, "Error getting streak document", task.getException());
                listener.onGetCurrentStreak(0);
            }
        });
    }

    public void getLengthCollection(final OnLengthCollectionListener listener) {
        Query query = db.collection(COLLECTION_PROFILE)
                .document(this.getUserId())
                .collection(COLLECTION_ENCONTERS);

        query.get(Source.SERVER).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    int count = snapshot.size();
                    listener.onLengthRetrieved(count);
                } else {
                    listener.onLengthRetrieved(0);
                }
            }
        });
    }

    public void getBestResult(final OnGetBestResultListener listener) {
        DocumentReference userRef = db.collection(COLLECTION_PROFILE).document(getUserId());
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Double bestResult = document.getDouble("bestResult");
                if (bestResult != null) {
                    listener.onGetBestResult(bestResult);
                } else {
                    listener.onGetBestResult(0);
                }
            } else {
                this.handleFirestoreError(LOG_TAG,"Error getting best result document", task.getException());
                listener.onGetBestResult(0);
            }
        });
    }

    public interface OnProfileDataListener {
        void onProfileDataLoaded(ProfileData profileData);
    }

    public interface OnGetCurrentStreakListener {
        void onGetCurrentStreak(int streak);
    }

    public interface OnLengthCollectionListener {
        void onLengthRetrieved(long length);
    }

    public interface OnGetBestResultListener {
        void onGetBestResult(double bestResult);
    }
}
