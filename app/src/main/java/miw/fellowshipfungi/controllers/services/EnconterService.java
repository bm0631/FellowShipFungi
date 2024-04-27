package miw.fellowshipfungi.controllers.services;

import com.google.firebase.firestore.FirebaseFirestore;

import miw.fellowshipfungi.models.EnconterEntity;


public class EnconterService {

    private static final String COLLECTION_PROFILE = "Profiles";
    private static final String COLLECTION_ENCONTERS = "Enconters";
    private String user;
    private FirebaseFirestore db;

    public EnconterService() {
        this.db = FirebaseFirestore.getInstance();
        this.user = AuthService.getInstance().getIdUserLogged();
    }

    public void saveEnconter(EnconterEntity enconterEntity) {
        db.collection(COLLECTION_PROFILE).document(user).collection(COLLECTION_ENCONTERS)
                .add(enconterEntity.getMap());
    }
}
