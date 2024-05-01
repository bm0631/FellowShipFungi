package miw.fellowshipfungi.controllers;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.R;
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

            // Mostrar la longitud de la colección
            lengthTextView.setText(getString(R.string.length_registered_mushrooms, (int) profileData.getLengthCollection()));

            // Mostrar la racha actual
            streakTextView.setText(getString(R.string.consecutive_days_of_curiosities, profileData.getStreak()));

            // Mostrar el mejor resultado
            bestResultTextView.setText(getString(R.string.best_result_profile, profileData.getBestResult() * 100));
        });
    }

}
