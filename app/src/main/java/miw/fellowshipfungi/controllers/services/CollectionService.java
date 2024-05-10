package miw.fellowshipfungi.controllers.services;


import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import miw.fellowshipfungi.models.ask.recognitionmodels.RecognitionEntity;
import miw.fellowshipfungi.models.profile.EnconterCollectionEntity;

public class CollectionService extends BaseService {

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
                .collection(COLLECTION_ENCONTERS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        List<EnconterCollectionEntity> collectionEntities = new ArrayList<>();
                        AtomicInteger loadedCount = new AtomicInteger(0);
                        int totalCount = task.getResult().size();
                        for (DocumentSnapshot document : task.getResult()) {
                            String enconterID = document.getId();
                            Map<String, Object> enconterData = document.getData();
                            String specieId = (String) enconterData.get("speciedId");
                            new RecognitionService().loadSpecie(specieId, new RecognitionService.RecognitionServiceCallback() {
                                @Override
                                public void onSuccess(RecognitionEntity recognitionEntity) {
                                    EnconterCollectionEntity entity = new EnconterCollectionEntity(enconterID, enconterData, recognitionEntity);
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

    public void deleteEnconter(String enconterId, OnDeleteEnconterListener listener) {
        db.collection(COLLECTION_PROFILE)
                .document(this.getUserId())
                .collection(COLLECTION_ENCONTERS)
                .document(enconterId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> enconterData = documentSnapshot.getData();
                        String nameImg = (String) enconterData.get("nameImg");
                        deleteImage(nameImg);
                    }
                    db.collection(COLLECTION_PROFILE)
                            .document(this.getUserId())
                            .collection(COLLECTION_ENCONTERS)
                            .document(enconterId)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Log.d("CollectionService", "Enconter successfully deleted!");
                                listener.onEnconterDeleted();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("CollectionService", "Error deleting enconter", e);
                                listener.onFailure("Error deleting enconter");
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("CollectionService", "Error retrieving enconter data", e);
                    listener.onFailure("Error retrieving enconter data");
                });
    }


    private void deleteImage(String nameImg) {
        if (nameImg != null && !nameImg.isEmpty()) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("EncontersImg/" + nameImg);
            storageRef.delete()
                    .addOnSuccessListener(aVoid -> Log.d("CollectionService", "Image successfully deleted: " + nameImg))
                    .addOnFailureListener(e -> Log.e("CollectionService", "Error deleting image: " + nameImg, e));
        }
    }

    public interface OnCollectionListener {
        void onCollectionLoaded(List<EnconterCollectionEntity> collection);

        void onFailure(String errorMessage);
    }

    public interface OnDeleteEnconterListener {
        void onEnconterDeleted();

        void onFailure(String errorMessage);
    }
}

