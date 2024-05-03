package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.dialogs.DatePickerFragment;
import miw.fellowshipfungi.controllers.services.EnconterService;
import miw.fellowshipfungi.models.EnconterEntity;

public class CreateEnconterActivity extends AppCompatActivity {

    private EnconterEntity enconterEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_enconter);

        String specieId = getIntent().getStringExtra("specieId");
        this.enconterEntity = new EnconterEntity(specieId);

        String specieName = getIntent().getStringExtra("specieName");
        ((TextView) findViewById(R.id.nameMusshroom)).setText(specieName);


        EditText etPlannedDate = (EditText) findViewById(R.id.etDate);
        etPlannedDate.setOnClickListener(this::showDatePickerDialog);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_recognition, menu);
        MenuItem stopButton = menu.findItem(R.id.opcStop);
        stopButton.setEnabled(false);
        stopButton.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opcBack) {
            Intent intent = new Intent(this, RecognitionActivity.class);
            intent.putExtra("Current", this.enconterEntity.getSpecieId());
            intent.putExtra("Previus", getIntent().getStringExtra("Previus"));
            intent.putExtra("countAsks", getIntent().getStringExtra("countAsks"));
            startActivity(intent);
            finishAffinity();
        }
        return true;
    }

    private void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void saveEnconter(View view) {
        String date = ((TextView) findViewById(R.id.etDate)).getText().toString();
        String location = ((TextView) findViewById(R.id.location)).getText().toString();
        String weather = ((Spinner) findViewById(R.id.weather)).getSelectedItem().toString();
        if (TextUtils.isEmpty(date)) {
            date = null;
        }
        if (TextUtils.isEmpty(location)) {
            location = null;
        }
        if (weather.equals(getString(R.string.select))) {
            weather = null;
        }

        this.enconterEntity.setDate(date);
        this.enconterEntity.setLocation(location);
        this.enconterEntity.setWeather(weather);

        EnconterService.getInstance().saveEnconter(this.enconterEntity);
        Toast.makeText(this, R.string.writtenEnconter, Toast.LENGTH_SHORT).show();

        this.returnMainMenu();

    }

    private void returnMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
