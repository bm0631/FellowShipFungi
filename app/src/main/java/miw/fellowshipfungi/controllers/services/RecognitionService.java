package miw.fellowshipfungi.controllers.services;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class RecognitionService {
    final static String LOG_TAG = "MiW Recognition";
    private static final String COLLECTION_NAME = "RecognitonMushroom";
    private static final String ASK_DOCUMENT = "Asks";
    private static final String ANSWER_DOCUMENT = "Answers";
    private static final String SPECIES_DOCUMENT = "Species";
    private static RecognitionService instance;
    private FirebaseFirestore db;
    private RecognitionEntity recognitionEntity;

    private RecognitionService() {
        this.db = FirebaseFirestore.getInstance();
        this.recognitionEntity = new RecognitionEntity();
    }

    public static RecognitionService getInstance() {
        if (instance == null) {
            instance = new RecognitionService();
        }
        return instance;
    }

    public void loadSpecie(String specieNode, RecognitionServiceCallback callback) {
        loadDocument(SPECIES_DOCUMENT, specieNode, callback);
    }

    public void loadAskAndAnswers(String askNode, RecognitionServiceCallback callback) {
        loadDocument(ASK_DOCUMENT, askNode, callback);
    }

    private void loadDocument(String documentName, String nodeName, RecognitionServiceCallback callback) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(documentName);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(nodeName);
                if (currentNodeData != null) {
                    if (documentName.equals(ASK_DOCUMENT)) {
                        recognitionEntity.setAskEntity(currentNodeData);
                        loadAnswers((List<String>) currentNodeData.get("nextNodes"), callback);
                    } else {
                        recognitionEntity.setMusshroomEntity(currentNodeData);
                        callback.onSuccess(recognitionEntity);
                    }
                } else {
                    callback.onFailure(new Exception(documentName + " data not found"));
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }

    private void loadAnswers(List<String> answerIds, RecognitionServiceCallback callback) {
        AtomicInteger loadedAnswers = new AtomicInteger(0);
        int totalAnswers = answerIds.size();

        for (String answerId : answerIds) {
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
    }

    public interface RecognitionServiceCallback {
        void onSuccess(RecognitionEntity recognitionEntity);

        void onFailure(Exception e);
    }
}
