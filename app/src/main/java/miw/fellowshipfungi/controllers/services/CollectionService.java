package miw.fellowshipfungi.controllers.services;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;
import miw.fellowshipfungi.models.profile.EncounterCollectionEntity;

public class CollectionService extends BaseService {
    private static String LOG_TAG = "CollectionService";
    private static CollectionService instance;

    public CollectionService() {
        super();
    }


    public static CollectionService getInstance() {
        if (instance == null) {
            instance = new CollectionService();
        }
        return instance;
    }

    public void getCollection(final OnCollectionListener listener) {
        db.collection(COLLECTION_PROFILE)
                .document(this.getUserId())
                .collection(COLLECTION_ENCOUNTERS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        List<EncounterCollectionEntity> collectionEntities = new ArrayList<>();
                        AtomicInteger loadedCount = new AtomicInteger(0);
                        int totalCount = task.getResult().size();
                        for (DocumentSnapshot document : task.getResult()) {
                            String encounterID = document.getId();
                            Map<String, Object> encounterData = document.getData();
                            String specieId = (String) encounterData.get("speciedId");
                            new RecognitionService().loadSpecie(specieId, new RecognitionService.RecognitionServiceCallback() {
                                @Override
                                public void onSuccess(RecognitionEntity recognitionEntity) {
                                    EncounterCollectionEntity entity = new EncounterCollectionEntity(encounterID, encounterData, recognitionEntity);
                                    collectionEntities.add(entity);
                                    if (loadedCount.incrementAndGet() == totalCount) {
                                        // Notificar al listener una vez que se hayan cargado todas las entidades
                                        listener.onCollectionLoaded(collectionEntities);
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    listener.onFailure("Error loading species data");
                                }
                            });
                        }
                    } else {
                        this.handleFirestoreError(LOG_TAG, "Error getting collection documents", task.getException());
                        listener.onFailure("Error getting collection documents");
                    }
                });
    }

    public void deleteEncounter(String encounterId, OnDeleteEncounterListener listener) {
        db.collection(COLLECTION_PROFILE)
                .document(this.getUserId())
                .collection(COLLECTION_ENCOUNTERS)
                .document(encounterId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> encounterData = documentSnapshot.getData();
                        String nameImg = (String) encounterData.get("nameImg");
                        deleteImage(nameImg);
                    }
                    db.collection(COLLECTION_PROFILE)
                            .document(this.getUserId())
                            .collection(COLLECTION_ENCOUNTERS)
                            .document(encounterId)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                listener.onEncounterDeleted();
                            })
                            .addOnFailureListener(e -> {
                                this.handleFirestoreError(LOG_TAG, "Error deleting encounter", e);
                                listener.onFailure("Error deleting encounter");
                            });
                })
                .addOnFailureListener(e -> {
                    this.handleFirestoreError(LOG_TAG, "Error retrieving encounter data", e);
                    listener.onFailure("Error retrieving encounter data");
                });
    }


    private void deleteImage(String nameImg) {
        if (nameImg != null && !nameImg.isEmpty()) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("EncountersImg/" + nameImg);
            storageRef.delete();
        }
    }

    public interface OnCollectionListener {
        void onCollectionLoaded(List<EncounterCollectionEntity> collection);

        void onFailure(String errorMessage);
    }

    public interface OnDeleteEncounterListener {
        void onEncounterDeleted();

        void onFailure(String errorMessage);
    }
}

