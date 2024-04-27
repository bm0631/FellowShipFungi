package miw.fellowshipfungi.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.R;

import miw.fellowshipfungi.controllers.services.TestService;
import miw.fellowshipfungi.models.ask_models.TestEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestActivity extends AppCompatActivity {
    final static String LOG_TAG = "Fellowship Fungi Test";
    private final String BASEID="Question_0";
    private final int numberQuestions=10;
    private final int numberQuestionsDB=50;
    private TestService testService;
    private List<TestEntity> testEntities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_test);
        this.testService=new TestService();
        this.testEntities=new ArrayList<>();

        Set<String> questionsId = new HashSet<>();
        Random random = new Random();

        while (questionsId.size() < this.numberQuestions) {
            int ramdonNumber = random.nextInt(this.numberQuestionsDB)+1;
            String extraZero="0";
            if (ramdonNumber>=10)
                extraZero="";
            questionsId.add(BASEID+extraZero+ramdonNumber);
        }
        this.loadQuestions(questionsId);
        this.startTest();
    }


    private void loadQuestions(Set<String> questionsId) {
        for (String questionId: questionsId) {
            this.loadQuestion(questionId);
        }
    }

    private void loadQuestion(String questionId) {
        this.testService.loadCuriosity(questionId, new TestService.TestServiceCallback() {
            @Override
            public void onSuccess(TestEntity testEntity) {
                TestActivity.this.testEntities.add(testEntity);
                if (testEntities.size() == TestActivity.this.numberQuestions) {
                    TestActivity.this.startTest();
                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.w(LOG_TAG, "Error loading Test: ", e);
            }
        });
    }

    private void startTest() {
    }

}
