package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.dialogs.DatePickerFragment;

public class CreateEnconterActivity extends AppCompatActivity {
    private String specieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_enconter);

        this.specieId = getIntent().getStringExtra("specieId");

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
            intent.putExtra("Current", this.specieId);
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
}
