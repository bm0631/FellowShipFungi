package miw.fellowshipfungi.controllers.services;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import miw.fellowshipfungi.models.ask.TestEntity;

public class TestService extends BaseService {
    final static String LOG_TAG = "Test Service";

    private static TestService instance;

    private TestEntity testEntity;

    private TestService() {
        super();
    }

    public static TestService getInstance() {
        if (instance == null) {
            instance = new TestService();
        }
        return instance;
    }


    public void loadCuriosity(String idQuestion, TestService.TestServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_TEST).document(TEST_DOCUMENT);
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
                this.handleFirestoreError(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    public void updateBestResult(Double newResult) {

        if (this.getUserId() == null) {
            this.handleFirestoreError(LOG_TAG, "User ID is null", null);
            return;
        }

        DocumentReference userRef =  this.getProfileDocument();

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Double bestResult = document.getDouble("bestResult");

                if (bestResult == null || newResult >= bestResult) {
                    Map<String, Object> streakData = new HashMap<>();
                    streakData.put("bestResult", newResult);
                    userRef.update(streakData);
                }

            } else {
                this.handleFirestoreError(LOG_TAG, "Error getting streak document", task.getException());
            }
        });
    }


    public interface TestServiceCallback {
        void onSuccess(TestEntity testEntity);

        void onFailure(Exception e);
    }
}
