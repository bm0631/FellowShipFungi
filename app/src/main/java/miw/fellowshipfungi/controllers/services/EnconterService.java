package miw.fellowshipfungi.controllers.services;

import com.google.firebase.firestore.FirebaseFirestore;

import miw.fellowshipfungi.models.EnconterEntity;


public class EnconterService {
    private static final String COLLECTION_PROFILE = "Profiles";
    private static final String COLLECTION_ENCONTERS = "Enconters";
    private static EnconterService instance;
    private String user;
    private FirebaseFirestore db;

    private EnconterService() {
        this.db = FirebaseFirestore.getInstance();
        this.user = AuthService.getInstance().getIdUserLogged();
    }

    public static EnconterService getInstance() {
        if (instance == null) {
            instance = new EnconterService();
        }
        return instance;
    }

    public void saveEnconter(EnconterEntity enconterEntity) {
        db.collection(COLLECTION_PROFILE).document(user).collection(COLLECTION_ENCONTERS)
                .add(enconterEntity.getMap());
    }
}
