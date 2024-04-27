package miw.fellowshipfungi.controllers.services;

import com.google.firebase.auth.FirebaseAuth;

public class AuthService {
    private static AuthService instance;
    private final FirebaseAuth mFirebaseAuth;

    private AuthService() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean isLogged() {
        return mFirebaseAuth.getCurrentUser() != null;
    }

    public String getIdUserLogged() {
        return mFirebaseAuth.getCurrentUser().getUid();
    }

    public String getUserName() {
        return mFirebaseAuth.getCurrentUser().getDisplayName();
    }
}
