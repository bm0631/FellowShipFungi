package miw.fellowshipfungi.controllers.services;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import miw.fellowshipfungi.models.CuriosityEntity;

public class CuriosityService {


    final static String LOG_TAG = " Curiosity Service";
    private static final String COLLECTION_NAME = "Daily Curiosity";
    private static final String CURIOSITY_DOCUMENT = "Curiosities";

    private FirebaseFirestore db;
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


    public interface CuriosityServiceCallback {
        void onSuccess(CuriosityEntity curiosityEntity);

        void onFailure(Exception e);
    }
}
