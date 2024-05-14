package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.dialogs.DatePickerFragment;
import miw.fellowshipfungi.controllers.services.EncounterService;
import miw.fellowshipfungi.models.EncounterEntity;

public class CreateEncounterActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 100;
    private EncounterEntity encounterEntity;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_encounter);

        String specieId = getIntent().getStringExtra("specieId");
        this.encounterEntity = new EncounterEntity(specieId);

        String specieName = getIntent().getStringExtra("specieName");
        ((TextView) findViewById(R.id.nameMusshroom)).setText(specieName);

        EditText etPlannedDate = findViewById(R.id.etDate);
        etPlannedDate.setOnClickListener(this::showDatePickerDialog);

        findViewById(R.id.imageView).setOnClickListener(this::selectImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            intent.putExtra("Current", this.encounterEntity.getSpecieId());
            intent.putExtra("Previus", getIntent().getStringExtra("Previus"));
            intent.putExtra("countAsks", getIntent().getStringExtra("countAsks"));
            startActivity(intent);
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void saveEncounter(View view) {
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

        if (!validateImage()) {
            return;
        }

        this.encounterEntity.setDate(date);
        this.encounterEntity.setLocation(location);
        this.encounterEntity.setWeather(weather);

        EncounterService.getInstance().saveEncounter(this.encounterEntity, this.imageUri);
        Toast.makeText(this, R.string.writtenEncounter, Toast.LENGTH_SHORT).show();

        returnMainMenu();
    }

    private boolean validateImage() {
        if (imageUri != null) {
            String fileType = getContentResolver().getType(imageUri);
            if (fileType != null && !fileType.equals("image/jpeg")) {
                Toast.makeText(this, "Por favor, seleccione una imagen en formato JPG (.jpg)", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void returnMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserIntent = Intent.createChooser(intent, "Seleciona Una Foto de la Seta");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            this.imageUri = data.getData();
            if (this.imageUri==null){
                this.imageUri = saveImageToGallery((Bitmap) data.getExtras().get("data"));
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
                Toast.makeText(this, "Error al capturar la imagen", Toast.LENGTH_SHORT).show();
            }
    }

    private Uri saveImageToGallery(Bitmap imageBitmap) {
        // Guardar la imagen capturada en un archivo temporal
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Devolver la URI del archivo guardado
        return Uri.fromFile(imageFile);
    }
}
