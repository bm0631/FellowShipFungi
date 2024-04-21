package miw.fellowshipfungi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.controllers.RecognitionActivity;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "Main Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(LOG_TAG, "Se pulsa Nuevo Recognition Musshrom: ");
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("Current", "Ask_001");
        intent.putExtra("countAsks", 1);
        startActivity(intent);
    }


}