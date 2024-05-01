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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.dialogs.YesCancelDialog;
import miw.fellowshipfungi.controllers.services.TestService;
import miw.fellowshipfungi.models.ask.TestEntity;

public class TestActivity extends AppCompatActivity {
    final static String LOG_TAG = "Fellowship Fungi Test";

    private final int NUMBERQUESTIONS = 10;

    private TestService testService;
    private List<TestEntity> testEntities;
    private int currentQuestionIndex;
    private ImageView viewImg;
    private TextView viewText;
    private List<Button> buttonViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_test);
        this.testService = new TestService();
        this.testEntities = new ArrayList<>();

        this.currentQuestionIndex = 0;

        this.onProgresionBar();
        this.loadViewElements();

        this.generateRandomQuestions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        YesCancelDialog dialog = YesCancelDialog.newInstance("Finalizar Test", "¿Quieres anular el Test?");
        dialog.setCallback(this::backMainMenu);
        dialog.show(getSupportFragmentManager(), "yes_no_dialog");
        return true;
    }


    private void loadViewElements() {
        final int NUMBER_ANSWERS = 4;

        this.viewImg = findViewById(R.id.questionTextImage);
        this.viewText = findViewById(R.id.questionText);

        this.buttonViews = new ArrayList<>();
        for (int i = 0; i < NUMBER_ANSWERS; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            this.buttonViews.add(findViewById(buttonId));
        }
    }

    private void generateRandomQuestions() {

        final String BASEID = "Question_0";
        final int NUMBER_QUESTION_DB = 50;

        Set<String> questionsId = new HashSet<>();
        Random random = new Random();

        while (questionsId.size() < this.NUMBERQUESTIONS) {
            int ramdonNumber = random.nextInt(NUMBER_QUESTION_DB) + 1;
            String extraZero = "0";
            if (ramdonNumber >= 10)
                extraZero = "";
            questionsId.add(BASEID + extraZero + ramdonNumber);
        }
        this.loadQuestions(questionsId);

    }

    private void loadQuestions(Set<String> questionsId) {
        for (String questionId : questionsId) {
            this.loadQuestion(questionId);
        }
    }

    private void loadQuestion(String questionId) {
        this.testService.loadCuriosity(questionId, new TestService.TestServiceCallback() {
            @Override
            public void onSuccess(TestEntity testEntity) {
                TestActivity.this.testEntities.add(testEntity);
                if (testEntities.size() == TestActivity.this.NUMBERQUESTIONS) {
                    TestActivity.this.printCurrentQuestion();
                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.w(LOG_TAG, "Error loading Test: ", e);
            }
        });
    }

    private void printCurrentQuestion() {
        this.onProgresionBar();
        TestEntity currentQuestion = this.testEntities.get(this.currentQuestionIndex);

        String imageUrl = currentQuestion.getImgUrl();
        Picasso.get().load(imageUrl).into(viewImg);

        this.viewText.setText(currentQuestion.getTextQuestion());

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < this.buttonViews.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        for (int i = 0; i < this.buttonViews.size(); i++) {
            int buttonIndex = indices.get(i);
            this.buttonViews.get(i).setText(currentQuestion.getTextAnwser(buttonIndex));
            this.buttonViews.get(i).setTag(currentQuestion.getIsTrueAnwser(buttonIndex));
        }

        this.offProgresionBar();
    }

    public void answerQuestion(View view) {
        boolean isCorrect = Boolean.parseBoolean(view.getTag().toString());
        Log.w("kkk", view.getTag().toString());
        TextView countRight = findViewById(R.id.countRight);
        TextView countWrong = findViewById(R.id.countWrong);
        TextView count = findViewById(R.id.count);

        int countNumberRight = Integer.parseInt(countRight.getText().toString());
        int countNumberWrong = Integer.parseInt(countWrong.getText().toString());

        if (isCorrect) {
            countRight.setText(String.valueOf(++countNumberRight)); // Incrementar y establecer el nuevo valor
        } else {
            countWrong.setText(String.valueOf(++countNumberWrong)); // Incrementar y establecer el nuevo valor
        }

        count.setText(String.valueOf(countNumberRight + countNumberWrong) + "/" + NUMBERQUESTIONS);

        this.currentQuestionIndex++;
        if (this.currentQuestionIndex >= this.NUMBERQUESTIONS) {
            this.printResults();
        } else {
            this.printCurrentQuestion();
        }
    }

    protected void onProgresionBar() {

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.testLayout).setVisibility(View.GONE);
    }

    private void offProgresionBar() {

        findViewById(R.id.progressBar).setVisibility(View.GONE);
        findViewById(R.id.testLayout).setVisibility(View.VISIBLE);
    }

    private void printResults() {
        this.setContentView(R.layout.activity_test_resul);

        TextView countRight = findViewById(R.id.countRight);


        Double result = Double.parseDouble(countRight.getText().toString()) / Double.parseDouble(String.valueOf(this.NUMBERQUESTIONS));


        String textResult = getString(R.string.porcentaje_de_aciertos, result * 100);
        ((TextView) findViewById(R.id.textPercentage)).setText(textResult);

        this.testService.updateBestResult(result);

    }

    public void rePlay(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void backMainMenu() {
        this.backMainMenu(new View(this));
    }

    public void backMainMenu(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
