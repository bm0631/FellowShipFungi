package miw.fellowshipfungi.controllers.services;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseService {
    protected static final String COLLECTION_PROFILE = "Profiles";
    protected static final String COLLECTION_ENCONTERS = "Enconters";
    protected static final String COLLECTION_CURIOSITY = "Daily Curiosity";
    protected static final String CURIOSITY_DOCUMENT = "Curiosities";
    protected static final String COLLECTION_TEST = "Test";
    protected static final String TEST_DOCUMENT = "Questions";
    protected static final String COLLECTION_RECOGNITION = "RecognitonMushroom";
    protected static final String ASK_DOCUMENT = "Asks";
    protected static final String ANSWER_DOCUMENT = "Answers";
    protected static final String SPECIES_DOCUMENT = "Species";
    protected FirebaseFirestore db;
    private String userId;
    private String nameUser;


    public BaseService() {
        this.db = FirebaseFirestore.getInstance();
        this.userId = AuthService.getInstance().getIdUserLogged();
        this.nameUser = AuthService.getInstance().getUserName();
    }

    protected String getUserId() {
        return this.userId;
    }

    protected String getName() {
        return this.nameUser;
    }

}
