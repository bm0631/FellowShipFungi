package miw.fellowshipfungi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.controllers.MainMenuActivity;
import miw.fellowshipfungi.controllers.RecognitionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
    }


}