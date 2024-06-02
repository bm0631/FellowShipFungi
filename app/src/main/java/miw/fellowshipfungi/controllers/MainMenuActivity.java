package miw.fellowshipfungi.controllers;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Random;

import miw.fellowshipfungi.R;
import miw.fellowshipfungi.controllers.services.AuthService;

public class MainMenuActivity extends AppCompatActivity {
    private final static String LOG_TAG = "Fellowship Main Menu";
    private final static String ROOT_IMG_URL = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/Tittle%2Ftitle_";
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

            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                AuthService authService = AuthService.getInstance();
                if (authService.isLogged()) {
                    Toast.makeText(MainMenuActivity.this, "Usuario  " + authService.getUserName(), Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG, false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build()
                                    ))
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
        finishAffinity();
    }

    public void onOpenCuriosityClick(View view) {
        Log.i(LOG_TAG, "Se pulsa Reconocimiento");
        Intent intent = new Intent(this, CuriosityActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void onOpenTestClick(View view) {
        Log.i(LOG_TAG, "Se pulsa TEST");
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void onOpenProfileClick(View view) {
        Log.i(LOG_TAG, "Se pulsa PROFILE");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    public void onOpenAboutClick(View view) {
        Log.i(LOG_TAG, "Se pulsa ABOUT");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.AboutTitle));


        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_about, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                        Toast.makeText(MainMenuActivity.this, "Cerrada Sesion", Toast.LENGTH_SHORT).show();

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
        if (AuthService.getInstance().isLogged()) {
            findViewById(R.id.invisibleRow).setVisibility(View.VISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.GONE);
        }

    }


}
