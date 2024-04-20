package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.adapters.AnswerAdapter;
import miw.fellowshipfungi.models.ask_models.recognitionmodels.AnswerEntity;
import miw.fellowshipfungi.models.ask_models.recognitionmodels.NodeTypes;
import miw.fellowshipfungi.models.ask_models.recognitionmodels.RecognitionEntity;

public class RecognitionActivity extends AppCompatActivity {

    final static String LOG_TAG = "MiW Recognition";
    private static final String COLLECTION_NAME = "RecognitonMushroom";
    private static final String ASK_DOCUMENT = "Asks";
    private static final String ANSWER_DOCUMENT = "Answers";
    private static final String SPECIES_DOCUMENT = "Species";


    private RecognitionEntity recognitionEntity;
    private FirebaseFirestore db;

    private String currentNode;

    private int countAsks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = FirebaseFirestore.getInstance();

        //TODO recoger el numero anterior para agarantizar la vuelta atras
        this.recognitionEntity = new RecognitionEntity();
        this.currentNode = getIntent().getStringExtra("Ask_id");
        this.countAsks = getIntent().getIntExtra("countAsks", 0);
        if (this.currentNode == null) {
            this.currentNode = getIntent().getStringExtra("Species_id");
            Log.w(LOG_TAG, "CurrentNode: " + this.currentNode);
            this.createViewMusshroom();
        } else {
            Log.w(LOG_TAG, "CurrentNode: " + this.currentNode);
            this.createViewAsk();
        }

        invalidateOptionsMenu();
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
                    this.printDataAsk();
                }
            } else {
                Log.w(LOG_TAG, "Error getting documents.", task.getException());
            }
        });
    }

    private void printDataAsk() {
        // Configurar la pregunta en el TextView
        TextView questionTextView = findViewById(R.id.askTestView);
        questionTextView.setText(this.recognitionEntity.getAskText());

        // Configurar las respuestas en el RecyclerView
        RecyclerView answersRecyclerView = findViewById(R.id.answersRecyclerView);
        AnswerAdapter answerAdapter = new AnswerAdapter(this.recognitionEntity.getAnswerEntities());
        answersRecyclerView.setAdapter(answerAdapter);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Configurar el contador de preguntas
        TextView countAsksTextView = findViewById(R.id.countAsks);
        countAsksTextView.setText(this.countAsks + " Pasos");
    }

    public void replied(View view) {
        if (view.getTag() != null) {
            String nextNodeId = view.getTag().toString();
            Log.w(LOG_TAG, "Answer replied: " + ((Button) view).getText().toString());
            Log.w(LOG_TAG, "Next NODE: " + nextNodeId);
            Log.w(LOG_TAG, "TYPE NODE: " + AnswerEntity.typeNode(nextNodeId));

            if (AnswerEntity.typeNode(nextNodeId) == NodeTypes.Ask) {
                //Se abre nueva pregunta
                Log.w(LOG_TAG, "NUEVA PREGUNTAAAA");
                Intent intent = new Intent(this, RecognitionActivity.class);
                intent.putExtra("Ask_id", view.getTag().toString());
                intent.putExtra("countAsks", this.countAsks + 1);
                startActivity(intent);
                finishAffinity();
            } else {
                //Se abre nueva pregunta
                Log.w(LOG_TAG, "ESPECIEEEE");
                Intent intent = new Intent(this, RecognitionActivity.class);
                intent.putExtra("Ask_id", "Ask_001");
                intent.putExtra("countAsks", this.countAsks + 1);
                startActivity(intent);
                finishAffinity();
            }
        } else {
            Log.w(LOG_TAG, "Tag is null WAIT");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_recognition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            case R.id.opcBack:

                return true;
            case R.id.opcStop:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return true;
    }
}



