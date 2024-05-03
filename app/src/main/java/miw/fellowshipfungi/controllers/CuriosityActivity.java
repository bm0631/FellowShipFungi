package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.services.CuriosityService;
import miw.fellowshipfungi.models.CuriosityEntity;


public class CuriosityActivity extends AppCompatActivity {
    final static String LOG_TAG = "Fellowship Fungi Curiosity";
    private final String baseIdCuriosity = "Curiosity_0";
    private CuriosityService curiosityService;
    private String idCuriosity;
    private CuriosityEntity curiosityEntity;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_curiosity);

        Log.w(LOG_TAG, "OPENED CURIOSITY");


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        this.curiosityService = CuriosityService.getInstance();
        this.idCuriosity = this.getDayIdCuriosity();
        Log.w(LOG_TAG, "Se Solicita la curiosidad: " + this.idCuriosity);
        this.curiosityService.loadCuriosity(this.idCuriosity, new CuriosityService.CuriosityServiceCallback() {
            @Override
            public void onSuccess(CuriosityEntity curiosityEntity) {
                CuriosityActivity.this.curiosityEntity = curiosityEntity;
                CuriosityActivity.this.printCuriosity();
            }

            @Override
            public void onFailure(Exception e) {
                Log.w(LOG_TAG, "Error loading Curiosity: ", e);
            }
        });

        this.updateStreakCuriosities();
    }


    private String getDayIdCuriosity() {
        Date today = new Date();
        SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
        String day = dayFormatter.format(today);

        return this.baseIdCuriosity + day;
    }

    private void printCuriosity() {
        TextView textCuriosity = findViewById(R.id.textCuriosity);
        textCuriosity.setText(this.curiosityEntity.getText());
        textCuriosity.setVisibility(View.VISIBLE);

        ImageView curiosityImage = findViewById(R.id.imgCuriosity);
        String imageUrl = this.curiosityEntity.getImgUrl();
        Picasso.get().load(imageUrl).into(curiosityImage);
        curiosityImage.setVisibility(View.VISIBLE);

        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        String formatToday = formatter.format(today);

        TextView date = findViewById(R.id.date);
        date.setText(formatToday);
        date.setVisibility(View.VISIBLE);

        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_curiosity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opcBack) {
            this.backMainMenu();
        } else if (item.getItemId() == R.id.optShare) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, this.curiosityEntity.getText());

            try {
                this.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    private void backMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void updateStreakCuriosities() {
        this.curiosityService.updateStreakCuriosities();
    }

}
