package miw.fellowshipfungi.controllers.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return null;
        }
    }

    public String getUserName() {
        return mFirebaseAuth.getCurrentUser().getDisplayName();
    }
}
