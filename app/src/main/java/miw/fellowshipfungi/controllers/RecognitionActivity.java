package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.adapters.AnswerAdapter;
import miw.fellowshipfungi.controllers.dialogs.YesCancelDialog;
import miw.fellowshipfungi.controllers.services.AuthService;
import miw.fellowshipfungi.controllers.services.RecognitionService;
import miw.fellowshipfungi.models.ask.recognitionmodels.NodeTypes;
import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;

public class RecognitionActivity extends AppCompatActivity {

    final static String LOG_TAG = "Fellowship Fungi Recognition";

    private RecognitionEntity recognitionEntity;
    private String currentNode;
    private String previusNode;
    private int countAsks;
    private RecognitionService recognitionService;
    private View progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.recognitionService = RecognitionService.getInstance();

        this.recognitionEntity = new RecognitionEntity();

        this.previusNode = getIntent().getStringExtra("Previus");
        this.currentNode = getIntent().getStringExtra("Current");
        this.countAsks = getIntent().getIntExtra("countAsks", 0);

        Log.w(LOG_TAG, "CurrentNode: " + this.currentNode);

        if (NodeTypes.typeNode(this.currentNode) == NodeTypes.Specie) {
            this.createViewMusshroom();
        } else {
            this.createViewAsk();
        }
    }

    private void createViewMusshroom() {
        Log.w(LOG_TAG, "It's Musshrom " + this.currentNode);
        this.setContentView(R.layout.activity_mushroom);
        this.setupProgresionBar();
        this.loadSpecie();
    }

    private void createViewAsk() {
        Log.w(LOG_TAG, "It's ASK " + this.currentNode);
        this.setContentView(R.layout.activity_recognition);
        setupProgresionBar();
        this.loadAskAndAnswers();
    }


    private void loadAskAndAnswers() {

        this.recognitionService.loadAskAndAnswers(this.currentNode, new RecognitionService.RecognitionServiceCallback() {
            @Override
            public void onSuccess(RecognitionEntity recognitionEntity) {
                RecognitionActivity.this.recognitionEntity = recognitionEntity;
                RecognitionActivity.this.printAsk();
            }

            @Override
            public void onFailure(Exception e) {
                Log.w(LOG_TAG, "Error loading specie: ", e);
            }
        });
    }


    private void printAsk() {
        // La pregunta en el TextView
        TextView questionTextView = findViewById(R.id.askTestView);
        questionTextView.setText(this.recognitionEntity.getAskText());
        questionTextView.setVisibility(View.VISIBLE);

        // Las respuestas en el RecyclerView
        RecyclerView answersRecyclerView = findViewById(R.id.answersRecyclerView);
        AnswerAdapter answerAdapter = new AnswerAdapter(this.recognitionEntity.getAnswerEntities());
        answersRecyclerView.setAdapter(answerAdapter);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answersRecyclerView.setVisibility(View.VISIBLE);

        this.printStepCount();

        this.offProgresionBar();

    }

    public void loadSpecie() {
        this.recognitionService.loadSpecie(this.currentNode, new RecognitionService.RecognitionServiceCallback() {
            @Override
            public void onSuccess(RecognitionEntity recognitionEntity) {
                RecognitionActivity.this.recognitionEntity = recognitionEntity;
                RecognitionActivity.this.printSpecie();
            }

            @Override
            public void onFailure(Exception e) {
                Log.w(LOG_TAG, "Error loading specie: ", e);
            }
        });
    }

    private void printSpecie() {
        TextView nameSpecie = findViewById(R.id.mushroomName);
        nameSpecie.setText(this.recognitionEntity.getMusshroomName());
        nameSpecie.setVisibility(View.VISIBLE);

        ImageView mushroomImage = findViewById(R.id.mushroomImage);
        String imageUrl = this.recognitionEntity.getMusshroomImgUrl();
        Picasso.get().load(imageUrl).into(mushroomImage);
        mushroomImage.setVisibility(View.VISIBLE);


        this.printStepCount();

        this.offProgresionBar();

        this.visibilityCreateEnconterAsk();

    }

    public void repliedAsk(View view) {
        if (view.getTag() != null) {
            String nextNodeId = view.getTag().toString();
            Log.w(LOG_TAG, "Answer replied: " + ((Button) view).getText().toString());
            Log.w(LOG_TAG, "Next NODE: " + nextNodeId);
            Log.w(LOG_TAG, "TYPE NODE: " + NodeTypes.typeNode(nextNodeId));

            this.navigateToNode(view.getTag().toString());

        } else {
            Log.w(LOG_TAG, "Tag is null WAIT");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_recognition, menu);

        // Anular el botón de retroceso si this.previusNode es null
        MenuItem backButton = menu.findItem(R.id.opcBack);
        if (this.previusNode == null) {
            backButton.setEnabled(false);
            backButton.setVisible(false);
        }
        // Anular el botón de stop si el nodo es Specie
        MenuItem stopButton = menu.findItem(R.id.opcStop);
        if (NodeTypes.typeNode(this.currentNode) == NodeTypes.Specie) {
            stopButton.setEnabled(false);
            stopButton.setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opcBack) {
            this.navigateToNode(previusNode);
        } else if (item.getItemId() == R.id.opcStop) {
            this.showYesNoDialog_Anular();
        }
        return true;
    }

    private void navigateToNode(String node) {
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("Current", node);
        if (node == this.previusNode) {
            intent.putExtra("countAsks", countAsks - 1);
        } else {
            intent.putExtra("Previus", this.currentNode);
            intent.putExtra("countAsks", this.countAsks + 1);
        }


        startActivity(intent);
        finishAffinity();
    }

    private void printStepCount() {
        TextView countAsksTextView = findViewById(R.id.countAsks);
        countAsksTextView.setText(this.countAsks + " Pasos");
    }

    protected void setupProgresionBar() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void offProgresionBar() {
        this.progressBar.setVisibility(View.GONE);
    }

    private void showYesNoDialog_Anular() {
        YesCancelDialog dialog = YesCancelDialog.newInstance("Anular", "¿Quieres anular el Reconocimiento?");
        dialog.setCallback(this::openMainMenu);
        dialog.show(getSupportFragmentManager(), "yes_no_dialog");
    }

    public void openMainMenu(View view) {
        this.openMainMenu();
    }

    private YesCancelDialog.YesNoDialogCallback openMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
        return null;
    }

    public void openCreateEnconter(View view) {
        Intent intent = new Intent(this, CreateEnconterActivity.class);
        intent.putExtra("specieId", this.recognitionEntity.getMusshroomId());
        intent.putExtra("specieName", this.recognitionEntity.getMusshroomName());
        intent.putExtra("Previus", this.previusNode);
        intent.putExtra("countAsks", this.countAsks);
        startActivity(intent);
        finishAffinity();
    }

    public void visibilityCreateEnconterAsk() {
        if (!AuthService.getInstance().isLogged()) {
            Button yesEnconterButton = findViewById(R.id.yesButton);
            yesEnconterButton.setEnabled(false);
            yesEnconterButton.setText(R.string.mustLogged);
            yesEnconterButton.setBackgroundResource(android.R.drawable.btn_default);
        }
    }
}





