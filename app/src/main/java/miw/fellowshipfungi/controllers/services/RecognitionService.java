package miw.fellowshipfungi.controllers.services;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class RecognitionService extends BaseService {
    private static final String LOG_TAG = "MiW Recognition";
    private RecognitionEntity recognitionEntity;

    public RecognitionService() {
        super();
        this.recognitionEntity = new RecognitionEntity();
    }

    public void loadSpecie(String specieNode, RecognitionServiceCallback callback) {
        loadDocument(SPECIES_DOCUMENT, specieNode, callback);
    }

    public void loadAskAndAnswers(String askNode, RecognitionServiceCallback callback) {
        loadDocument(ASK_DOCUMENT, askNode, callback);
    }

    private void loadDocument(String documentName, String nodeName, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_RECOGNITION).document(documentName);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(nodeName);
                if (currentNodeData != null) {
                    if (documentName.equals(ASK_DOCUMENT)) {
                        recognitionEntity.setAskEntity(currentNodeData);
                        loadAnswers((List<String>) currentNodeData.get("nextNodes"), callback);
                    } else {
                        recognitionEntity.setMushroomEntity(currentNodeData);
                        callback.onSuccess(recognitionEntity);
                    }
                } else {
                    callback.onFailure(new Exception(documentName + " data not found"));
                }
            } else {
                this.handleFirestoreError(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    private void loadAnswers(List<String> answerIds, RecognitionServiceCallback callback) {
        AtomicInteger loadedAnswers = new AtomicInteger(0);
        int totalAnswers = answerIds.size();

        for (String answerId : answerIds) {
            loadAnswer(answerId, loadedAnswers, totalAnswers, callback);
        }
    }

    private void loadAnswer(String answerId, AtomicInteger loadedAnswers, int totalAnswers, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_RECOGNITION).document(ANSWER_DOCUMENT);
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
                this.handleFirestoreError(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    public interface RecognitionServiceCallback {
        void onSuccess(RecognitionEntity recognitionEntity);

        void onFailure(Exception e);
    }
}
