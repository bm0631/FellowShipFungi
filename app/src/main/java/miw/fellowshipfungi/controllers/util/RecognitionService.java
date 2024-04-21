package miw.fellowshipfungi.controllers.util;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask_models.recognitionmodels.RecognitionEntity;

public class RecognitionService {

    final static String LOG_TAG = "MiW Recognition";
    private static final String COLLECTION_NAME = "RecognitonMushroom";
    private static final String ASK_DOCUMENT = "Asks";
    private static final String ANSWER_DOCUMENT = "Answers";
    private static final String SPECIES_DOCUMENT = "Species";


    private FirebaseFirestore db;
    private RecognitionEntity recognitionEntity;

    public RecognitionService() {
        this.db = FirebaseFirestore.getInstance();
        this.recognitionEntity = new RecognitionEntity();
    }

    public void loadSpecie(String specieNode, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(SPECIES_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(specieNode);
                if (currentNodeData != null) {
                    recognitionEntity.setMusshroomEntity(currentNodeData);
                    callback.onSuccess(recognitionEntity);
                } else {
                    callback.onFailure(new Exception("Specie data not found"));
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    public void loadAskAndAnswers(String askNode, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(ASK_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(askNode);
                if (currentNodeData != null) {
                    recognitionEntity.setAskEntity(currentNodeData);

                    List<String> nextNodes = (List<String>) currentNodeData.get("nextNodes");
                    AtomicInteger loadedAnswers = new AtomicInteger(0);
                    int totalAnswers = nextNodes.size();

                    for (String answerNode : nextNodes) {
                        loadAnswer(answerNode, loadedAnswers, totalAnswers, callback);
                    }
                } else {
                    callback.onFailure(new Exception("Ask data not found"));
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    private void loadAnswer(String answerId, AtomicInteger loadedAnswers, int totalAnswers, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(ANSWER_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(answerId);
                recognitionEntity.addAnswer(currentNodeData);

                int loadedCount = loadedAnswers.incrementAndGet();
                if (loadedCount == totalAnswers) {
                    callback.onSuccess(recognitionEntity);
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    public interface RecognitionServiceCallback {
        void onSuccess(RecognitionEntity recognitionEntity);

        void onFailure(Exception e);
    }

}
