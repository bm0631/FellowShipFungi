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
import miw.fellowshipfungi.controllers.adapters.EnconterAdapter;
import miw.fellowshipfungi.controllers.services.CollectionService;
import miw.fellowshipfungi.models.profile.EnconterCollectionEntity;

public class CollectionActivity extends AppCompatActivity {

    private List<EnconterCollectionEntity> enconterCollectionEntities;
    private EnconterAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);


        this.loadCollectionData();
    }

    private void loadCollectionData() {
        CollectionService.getInstance().getCollection(new CollectionService.OnCollectionListener() {
            @Override
            public void onCollectionLoaded(List<EnconterCollectionEntity> collection) {

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
        Log.d("CollectionActivitaaay", this.enconterCollectionEntities.toString());
        RecyclerView recyclerView = findViewById(R.id.enconterRecyclerView);
        EnconterAdapter enconterAdapter = new EnconterAdapter(this.enconterCollectionEntities);
        recyclerView.setAdapter(enconterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setListEntities(List<EnconterCollectionEntity> collection) {
        Log.d("CollectionActivity", collection.toString());
        this.enconterCollectionEntities = collection;
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

    public void deleteEnconter(View view) {
        //TODO
    }

}

