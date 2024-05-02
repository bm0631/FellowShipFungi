package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.dialogs.YesCancelDialog;
import miw.fellowshipfungi.controllers.services.ProfileService;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);
        this.loadLayout();
    }

    private void loadLayout() {
        ProfileService profileService = new ProfileService();

        // Cargar los datos del perfil
        profileService.getProfileData(profileData -> {
            TextView lengthTextView = findViewById(R.id.lenghtCollectionTextView);
            TextView streakTextView = findViewById(R.id.streakCuriositiesTextView);
            TextView bestResultTextView = findViewById(R.id.bestResultTextView);
            TextView userName = findViewById(R.id.userName);
            // Mostrar la longitud de la colección
            lengthTextView.setText(getString(R.string.length_registered_mushrooms, (int) profileData.getLengthCollection()));

            // Mostrar la racha actual
            streakTextView.setText(getString(R.string.consecutive_days_of_curiosities, profileData.getStreak()));

            // Mostrar el mejor resultado
            bestResultTextView.setText(getString(R.string.best_result_profile, profileData.getBestResult() * 100));
            // Mostar nombre del usuario
            userName.setText(profileData.getUsername());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
        return true;
    }
    public void openCollection (View view){
        Log.w("lll","----");
        Intent intent = new Intent(this, CollectionActivity.class);
        startActivity(intent);
        finishAffinity();
    }

}
