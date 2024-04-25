package miw.fellowshipfungi.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Random;

import miw.fellowshipfungi.R;

public class MainMenuActivity extends AppCompatActivity {
    final static String LOG_TAG = "Fellowship Main Menu";
    final static String ROOT_IMG_URL = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Tittle%2Ftitle_";
    private static final int RC_SIGN_IN = 2019;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_menu);
        this.loadTittleImg();
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.setVisibilyAuth();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            FirebaseUser user = mFirebaseAuth.getCurrentUser();

            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Log.w(LOG_TAG, "HAY USER");
                    Toast.makeText(MainMenuActivity.this, "Usuario  " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.w(LOG_TAG, "ARRANCA LOGIN/REGISTER");
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG, false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .setLogo(R.mipmap.ic_launcher).setTheme(R.style.Base_Theme_FellowshipFungi)
                                    .build(),
                            RC_SIGN_IN
                    );

                }
            }
        };
    }


    private void loadTittleImg() {
        ImageView mushroomImage = findViewById(R.id.logoImageView);
        String imageUrl = ROOT_IMG_URL + (new Random().nextInt(5) + 1) + ".png?alt=media";
        Picasso.get().load(imageUrl).into(mushroomImage);
    }

    public void onRecognitionClick(View view) {
        Log.i(LOG_TAG, "Se pulsa Nuevo Recognition Musshrom");
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("Current", "Ask_001");
        intent.putExtra("countAsks", 1);
        startActivity(intent);
    }

    public void onOpenCuriosityClick(View view) {
        //TODO
    }

    public void onOpenTestClick(View view) {
        //TODO
    }

    public void onOpenProfileClick(View view) {
        //TODO
    }

    public void onLoginClick(View view) {

        this.mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        Intent i = new Intent(MainMenuActivity.this, MainMenuActivity.class);
        startActivity(i);
        finishAffinity();


    }

    public void onLogoutClick(View view) {
        AuthUI.getInstance()
                .signOut(MainMenuActivity.this)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        // below method is used after logout from device.
                        Toast.makeText(MainMenuActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();

                        // below line is to go to MainActivity via an intent.
                        Intent i = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                        startActivity(i);
                        finishAffinity();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setVisibilyAuth();
    }

    private void setVisibilyAuth() {
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            findViewById(R.id.invisibleRow).setVisibility(View.VISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.GONE);
        }

    }


}
