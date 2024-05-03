package miw.fellowshipfungi.controllers.services;

import miw.fellowshipfungi.models.EnconterEntity;


public class EnconterService extends BaseService {

    private static EnconterService instance;


    private EnconterService() {
        super();
    }

    public static EnconterService getInstance() {
        if (instance == null) {
            instance = new EnconterService();
        }
        return instance;
    }

    public void saveEnconter(EnconterEntity enconterEntity) {
        db.collection(COLLECTION_PROFILE).document(getUserId()).collection(COLLECTION_ENCONTERS)
                .add(enconterEntity.getMap());
    }
}
