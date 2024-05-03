package miw.fellowshipfungi.controllers.services;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import miw.fellowshipfungi.models.CuriosityEntity;

public class CuriosityService extends BaseService {

    final static String LOG_TAG = "Curiosity Service";
    private static CuriosityService instance;
    private CuriosityEntity curiosityEntity;

    private CuriosityService() {
        super();
    }

    public static CuriosityService getInstance() {
        if (instance == null) {
            instance = new CuriosityService();
        }
        return instance;
    }

    public void loadCuriosity(String idCuriosity, CuriosityService.CuriosityServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_CURIOSITY).document(CURIOSITY_DOCUMENT);

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

        if (this.getUserId() == null) {
            Log.e(LOG_TAG, "User ID is null");
            return;
        }

        DocumentReference userRef = db.collection(COLLECTION_PROFILE).document(getUserId());

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Long currentStreak = document.getLong("streak");
                Date streakLastDate = document.getDate("streakLastDate");
                int currentStreakInt = 1;

                if (currentStreak == null) {
                    streakLastDate = new Date();
                } else if (isSameDay(streakLastDate, new Date())) {
                    return;
                } else if (this.isYesterday(streakLastDate)) {
                    currentStreakInt = currentStreak.intValue() + 1;
                    streakLastDate = new Date();
                } else {
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
