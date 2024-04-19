package miw.fellowshipfungi.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.models.ask_models.recognitionmodels.RecognitionEntity;

public class RecognitionActivity extends Activity {

    final static String LOG_TAG = "MiW Recognition";
    private static final String COLLECTION_NAME = "RecognitonMushroom";
    private static final String ASK_DOCUMENT = "Asks";
    private static final String ANSWER_DOCUMENT = "Answers";
    private static final String SPECIES_DOCUMENT = "Species";


    private RecognitionEntity recognitionEntity;
    private FirebaseFirestore db;

    private String currentNode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = FirebaseFirestore.getInstance();
        this.recognitionEntity = new RecognitionEntity();
        this.currentNode = getIntent().getStringExtra("Ask_id");
        if (this.currentNode == null) {
            this.currentNode = getIntent().getStringExtra("Species_id");
            Log.w(LOG_TAG, "CurrentNode: " + this.currentNode);
            this.createViewMusshroom();
        } else {
            Log.w(LOG_TAG, "CurrentNode: " + this.currentNode);
            this.createViewAsk();
        }
    }

    private void createViewMusshroom() {
    }

    private void createViewAsk() {
        Log.w(LOG_TAG, "It's ASK " + this.currentNode);
        this.setContentView(R.layout.activity_recognition);
        this.loadAsk_And_Answers();


    }


    private void loadAsk_And_Answers() {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(ASK_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();

                Log.w(LOG_TAG, "DATOS ASK: " + result.get(this.currentNode));
                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(this.currentNode);
                this.recognitionEntity.setAskEntity(currentNodeData);

                List<String> nextNodes = (List<String>) currentNodeData.get("nextNodes");
                int totalAnswers = nextNodes.size();
                AtomicInteger loadedAnswers = new AtomicInteger(0);

                for (String answerNode : nextNodes) {
                    Log.w(LOG_TAG, "Cargando respuesta: " + answerNode);
                    this.loadAnswer(answerNode, loadedAnswers, totalAnswers);
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
            }
        });
    }

    private void loadAnswer(String answerId, AtomicInteger loadedAnswers, int totalAnswers) {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(ANSWER_DOCUMENT);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot result = task.getResult();

                Map<String, Object> currentNodeData = (Map<String, Object>) result.get(answerId);
                this.recognitionEntity.addAnswer(currentNodeData);

                int loadedCount = loadedAnswers.incrementAndGet();
                if (loadedCount == totalAnswers) {
                    // Todas las respuestas han sido cargadas
                    Log.w(LOG_TAG, "ResultDeCARGA: " + this.recognitionEntity.toString());
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
            }
        });
    }


    /*public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }*/

}



