package miw.fellowshipfungi.controllers.services;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


import miw.fellowshipfungi.models.ask_models.TestEntity;

public class TestService {


    final static String LOG_TAG = "Test Service";
    private static final String COLLECTION_NAME = "Test";
    private static final String TEST_DOCUMENT = "Questions";

    private final FirebaseFirestore db;
    private TestEntity testEntity;

    public TestService() {
        this.db = FirebaseFirestore.getInstance();

    }

    public void loadCuriosity(String idQuestion, TestService.TestServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(TEST_DOCUMENT);
        Log.w(LOG_TAG,"Questions Request: "+idQuestion);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(idQuestion);
                if (currentNodeData != null) {
                    this.testEntity = new TestEntity(currentNodeData);
                    callback.onSuccess(this.testEntity);

                } else {
                    callback.onFailure(new Exception(TEST_DOCUMENT + " data not found"));
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }



    public interface TestServiceCallback {
        void onSuccess(TestEntity testEntity);

        void onFailure(Exception e);
    }
}
