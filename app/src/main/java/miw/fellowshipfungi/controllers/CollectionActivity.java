package miw.fellowshipfungi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.adapters.EncounterAdapter;
import miw.fellowshipfungi.controllers.dialogs.YesCancelDialog;
import miw.fellowshipfungi.controllers.services.CollectionService;
import miw.fellowshipfungi.models.profile.EncounterCollectionEntity;

public class CollectionActivity extends AppCompatActivity {

    private List<EncounterCollectionEntity> encounterCollectionEntities;
    private String encounterToDelete;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);


        this.loadCollectionData();
    }

    private void loadCollectionData() {
        CollectionService.getInstance().getCollection(new CollectionService.OnCollectionListener() {
            @Override
            public void onCollectionLoaded(List<EncounterCollectionEntity> collection) {

                CollectionActivity.this.setListEntities(collection);
                CollectionActivity.this.setAdapter();

                Log.d("CollectionActivity", "La colección se ha cargado completamente.");
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("CollectionActivity", "Error al cargar la colección: " + errorMessage);
            }
        });
    }

    private void setAdapter() {
        Log.d("CollectionActivitaaay", this.encounterCollectionEntities.toString());
        RecyclerView recyclerView = findViewById(R.id.encounterRecyclerView);
        EncounterAdapter encounterAdapter = new EncounterAdapter(this.encounterCollectionEntities);
        recyclerView.setAdapter(encounterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setListEntities(List<EncounterCollectionEntity> collection) {
        Log.d("CollectionActivity", collection.toString());
        this.encounterCollectionEntities = collection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finishAffinity();
        return true;
    }

    public void deleteEncounter(View view) {

        this.encounterToDelete = view.getTag().toString();

        YesCancelDialog dialog = YesCancelDialog.newInstance("Borrar ", "¿Quieres borrar el Encuentro?");

        dialog.setCallback(this::deleteEncounterConfirmed);
        dialog.show(getSupportFragmentManager(), "yes_no_dialog");
    }

    public void deleteEncounterConfirmed() {

        String encounterIdToDelete = this.encounterToDelete;
        this.encounterToDelete = null;

        CollectionService.getInstance().deleteEncounter(encounterIdToDelete, new CollectionService.OnDeleteEncounterListener() {
            @Override
            public void onEncounterDeleted() {
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("CollectionActivity", "Error deleting encounter: " + errorMessage);
            }
        });
    }


}

