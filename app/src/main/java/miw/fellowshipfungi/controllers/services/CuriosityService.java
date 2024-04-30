package miw.fellowshipfungi.controllers.services;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import miw.fellowshipfungi.models.CuriosityEntity;

public class CuriosityService {


    final static String LOG_TAG = "Curiosity Service";
    private static final String COLLECTION_NAME = "Daily Curiosity";
    private static final String CURIOSITY_DOCUMENT = "Curiosities";

    private final FirebaseFirestore db;
    private CuriosityEntity curiosityEntity;

    public CuriosityService() {
        this.db = FirebaseFirestore.getInstance();

    }

    public void loadCuriosity(String idCuriosity, CuriosityService.CuriosityServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(CURIOSITY_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(idCuriosity);
                if (currentNodeData != null) {
                    this.curiosityEntity = new CuriosityEntity(currentNodeData);
                    callback.onSuccess(this.curiosityEntity);

                } else {
                    callback.onFailure(new Exception(CURIOSITY_DOCUMENT + " data not found"));
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    public void updateStreakCuriosities() {
        final String COLLECTION_PROFILE = "Profiles";
        String userId = AuthService.getInstance().getIdUserLogged();
        if (userId == null) {
            Log.e(LOG_TAG, "User ID is null");
            return;
        }

        DocumentReference userRef = db.collection(COLLECTION_PROFILE).document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Long currentStreak = document.getLong("streak");
                Date streakLastDate = document.getDate("streakLastDate");
                int currentStreakInt = 1;

                if (currentStreak == null || !this.isYesterday(streakLastDate)) {
                    streakLastDate = new Date();
                } else if (isSameDay(streakLastDate, new Date())) {
                    return;
                } else {
                    currentStreakInt = currentStreak.intValue() + 1;
                    streakLastDate = new Date();
                }


                Map<String, Object> streakData = new HashMap<>();
                streakData.put("streak", currentStreakInt);
                streakData.put("streakLastDate", streakLastDate);
                userRef.update(streakData);


            } else {
                Log.e(LOG_TAG, "Error getting streak document", task.getException());
            }
        });

    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isYesterday(Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.add(Calendar.DAY_OF_MONTH, 1);

        return this.isSameDay(cal1.getTime(), new Date());
    }

    public interface CuriosityServiceCallback {
        void onSuccess(CuriosityEntity curiosityEntity);

        void onFailure(Exception e);
    }
}
