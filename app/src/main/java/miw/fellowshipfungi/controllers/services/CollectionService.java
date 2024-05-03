package miw.fellowshipfungi.controllers.services;


import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;
import miw.fellowshipfungi.models.profile.EnconterCollectionEntity;

public class CollectionService {

    private static final String COLLECTION_PROFILE = "Profiles";
    private static final String COLLECTION_ENCONTERS = "Enconters";
    private static CollectionService instance;
    private String user;
    private FirebaseFirestore db;

    public CollectionService() {
        this.db = FirebaseFirestore.getInstance();
        this.user = AuthService.getInstance().getIdUserLogged();
    }


    public static CollectionService getInstance() {
        if (instance == null) {
            instance = new CollectionService();
        }
        return instance;
    }

    public void getCollection(final OnCollectionListener listener) {
        Log.w("lll", "ENTRO");
        db.collection(COLLECTION_PROFILE)
                .document(user)
                .collection(COLLECTION_ENCONTERS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.w("lll", "TENGO LOS ENCUENTROS");
                        List<EnconterCollectionEntity> collectionEntities = new ArrayList<>();
                        AtomicInteger loadedCount = new AtomicInteger(0);
                        int totalCount = task.getResult().size();
                        for (DocumentSnapshot document : task.getResult()) {
                            Log.w("lll", "ECUENTRO: " + document.getData());
                            Map<String, Object> enconterData = document.getData();
                            String specieId = (String) enconterData.get("speciedId");
                            new RecognitionService().loadSpecie(specieId, new RecognitionService.RecognitionServiceCallback() {
                                @Override
                                public void onSuccess(RecognitionEntity recognitionEntity) {
                                    Log.w("lll", "SETA: " + recognitionEntity.getMusshroomName());
                                    EnconterCollectionEntity entity = new EnconterCollectionEntity(enconterData, recognitionEntity);
                                    collectionEntities.add(entity);
                                    if (loadedCount.incrementAndGet() == totalCount) {
                                        // Notificar al listener una vez que se hayan cargado todas las entidades
                                        listener.onCollectionLoaded(collectionEntities);
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.e("CollectionService", "Error loading species data", e);
                                    listener.onFailure("Error loading species data");
                                }
                            });
                        }
                    } else {
                        Log.e("CollectionService", "Error getting collection documents", task.getException());
                        listener.onFailure("Error getting collection documents");
                    }
                });
    }


    public interface OnCollectionListener {
        void onCollectionLoaded(List<EnconterCollectionEntity> collection);

        void onFailure(String errorMessage);
    }
}

